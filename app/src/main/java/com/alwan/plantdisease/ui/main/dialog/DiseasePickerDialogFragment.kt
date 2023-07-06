package com.alwan.plantdisease.ui.main.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.alwan.plantdisease.R
import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import com.alwan.plantdisease.core.domain.entity.weather.Disease
import com.alwan.plantdisease.databinding.DialogDiseasePickerBinding
import com.alwan.plantdisease.ui.camera.CameraActivity
import com.alwan.plantdisease.ui.detail.DetailActivity
import com.alwan.plantdisease.ui.main.MainActivity
import com.alwan.plantdisease.ui.main.MainViewModel
import com.alwan.plantdisease.util.Data
import com.alwan.plantdisease.util.showToast
import com.alwan.plantdisease.util.toFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class DiseasePickerDialogFragment : DialogFragment() {
    private var _binding: DialogDiseasePickerBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var detailIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDiseasePickerBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailIntent = Intent(requireContext(), DetailActivity::class.java)
        initWindow()
    }

    private fun initWindow() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.attributes?.windowAnimations = R.style.DialogAnimation
        }

        showLoadingProcessingImage(false)
        showImageResult(false)
        isCancelable = true

        binding.tvCamera.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay(DISMISS_DELAY)
                requireActivity().runOnUiThread { startCameraX() }
            }
        }
        binding.tvGallery.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay(DISMISS_DELAY)
                requireActivity().runOnUiThread { startGallery() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingProcessingImage(state: Boolean) = with(dialog) {
        dialog?.setCancelable(!state)

        binding.llLoading.visibility =
            if (state) View.VISIBLE else View.GONE
        showImageResult(!state)
        binding.clPicker.visibility =
            if (state) View.GONE else View.VISIBLE
    }

    private fun showImageResult(state: Boolean) = with(dialog) {
        binding.llResult.visibility =
            if (state) View.VISIBLE else View.GONE
        binding.tvTitle.text =
            getString(if (state) R.string.picker_repick else R.string.picker_title)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        @Suppress("DEPRECATION")
        if (result.resultCode == MainActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("picture", File::class.java)
            } else {
                result.data?.getSerializableExtra("picture") as File
            }
            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val bitmap = BitmapFactory.decodeFile(myFile?.path)

            val requestFile = myFile?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
            val imagePart =
                requestFile?.let { MultipartBody.Part.createFormData("image", myFile.name, it) }

            showImageResult(true)
            binding.imgResult.setImageBitmap(bitmap)
            binding.btnSubmit.setOnClickListener { processImage(imagePart) }
        }
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            val myFile = selectedImg.toFile(requireContext())

            showImageResult(true)
            binding.imgResult.setImageURI(selectedImg)
            val requestFile = myFile.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
            val imagePart =
                requestFile.let { MultipartBody.Part.createFormData("image", myFile.name, it) }
            binding.btnSubmit.setOnClickListener { processImage(imagePart) }
        }
    }

    private fun handleClassifyPotato(image: MultipartBody.Part) {
        mainViewModel.classifyPotato(image).observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    showLoadingProcessingImage(true)
                }

                is Resource.Success -> {
                    showLoadingProcessingImage(false)
                    handlePotatoDetailIntent(res.data)
                    dialog?.dismiss()
                }

                is Resource.Error -> {
                    showLoadingProcessingImage(false)
                    requireActivity().showToast(res.message)
                }
            }
        }
    }

    private fun handleClassifyCorn(image: MultipartBody.Part) {
        mainViewModel.classifyCorn(image).observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    showLoadingProcessingImage(true)
                }

                is Resource.Success -> {
                    showLoadingProcessingImage(false)
                    handleCornDetailIntent(res.data)
                    dialog?.dismiss()
                }

                is Resource.Error -> {
                    showLoadingProcessingImage(false)
                    requireActivity().showToast(res.message)
                }
            }
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun processImage(image: MultipartBody.Part?) {
        mainViewModel.getPlantCategory().observe(viewLifecycleOwner) { res ->
            image?.let {
                if (res.equals("Corn"))
                    handleClassifyCorn(it)
                else
                    handleClassifyPotato(it)
            } ?: requireActivity().showToast("Undefined plant category")
        }
    }

    private fun handlePotatoDetailIntent(disease: PotatoDisease?) {
        val listPotatoDisease = Data.getPotatoDisease()

        when (disease?.diseasePredicted) {
            "Early Blight" -> detailIntent.putExtra(
                DetailActivity.DISEASE,
                listPotatoDisease[0]
            )

            "Late Blight" -> detailIntent.putExtra(
                DetailActivity.DISEASE,
                listPotatoDisease[1]
            )

            else -> {
                requireActivity().showToast("No disease listed" + disease.toString()    )
                return
            }
        }

        requireActivity().startActivity(detailIntent)
    }

    private fun handleCornDetailIntent(disease: CornDisease?) {
        val listCornDisease = Data.getCornDisease()

        when (disease?.diseasePredicted) {
            "Blight" -> detailIntent.putExtra(
                DetailActivity.DISEASE,
                listCornDisease[0],
            )

            "Common Rust" -> detailIntent.putExtra(
                DetailActivity.DISEASE,
                listCornDisease[1],
            )

            "Gray Leaf Spot" -> detailIntent.putExtra(
                DetailActivity.DISEASE,
                listCornDisease[2],
            )

            else -> {
                requireActivity().showToast("No disease listed / Plant is healthy")
                return
            }

        }
        requireActivity().startActivity(detailIntent)

    }

    companion object {
        private const val DISMISS_DELAY = 200L
    }
}