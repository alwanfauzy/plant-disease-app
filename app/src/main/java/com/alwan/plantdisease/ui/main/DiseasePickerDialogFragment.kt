package com.alwan.plantdisease.ui.main

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
import com.alwan.plantdisease.R
import com.alwan.plantdisease.databinding.DialogDiseasePickerBinding
import com.alwan.plantdisease.ui.camera.CameraActivity
import com.alwan.plantdisease.ui.detail.DetailActivity
import com.alwan.plantdisease.util.DummyData
import com.alwan.plantdisease.util.toFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class DiseasePickerDialogFragment : DialogFragment() {
    private var _binding: DialogDiseasePickerBinding? = null
    private val binding get() = _binding!!

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
    ) {
        @Suppress("DEPRECATION")
        if (it.resultCode == MainActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                it.data?.getSerializableExtra("picture") as File
            }
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = BitmapFactory.decodeFile(myFile?.path)

            showImageResult(true)
            binding.imgResult.setImageBitmap(result)
            binding.btnSubmit.setOnClickListener { processImage() }
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
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun processImage() {
        CoroutineScope(Dispatchers.Main).launch {
            showLoadingProcessingImage(true)
            dialog?.setCancelable(false)
            delay(5000L)
            dialog?.dismiss()
            val detailIntent = Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(
                    DetailActivity.DISEASE,
                    DummyData.getDummiesDisease()[0]
                )
            }
            requireContext().startActivity(detailIntent)
        }
    }

    companion object {
        private const val DISMISS_DELAY = 200L
    }
}