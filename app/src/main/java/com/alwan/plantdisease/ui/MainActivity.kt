package com.alwan.plantdisease.ui

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.plantdisease.R
import com.alwan.plantdisease.databinding.ActivityMainBinding
import com.alwan.plantdisease.domain.entity.Disease
import com.alwan.plantdisease.ui.camera.CameraActivity
import com.alwan.plantdisease.ui.detail.DetailActivity
import com.alwan.plantdisease.util.DummyData
import com.alwan.plantdisease.util.MarginItemDecoration
import com.alwan.plantdisease.util.showToast
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity(), DiseaseAdapter.DiseaseCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val diseaseAdapter = DiseaseAdapter(this)
    private var _dialog: Dialog? = null
    private val dialog get() = _dialog!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _dialog = Dialog(this)

        initVm()
        initView()
    }

    private fun initView() = with(binding) {
        tvWelcome.text = resources.getString(R.string.welcome_text, "Alwan")
        tvLocation.text = "Gresik"
        tvTemp.text = resources.getString(R.string.temp_value, "32.8")
        tvHumid.text = resources.getString(R.string.humid_value, "60")
        tvRain.text = resources.getString(R.string.rain_value, "4.2")
        tvWind.text = resources.getString(R.string.wind_value, "3.9")
        initRv()

        cvDiagnose.setOnClickListener {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                showDialogDiseasePicker()
            }
        }
    }

    private fun initRv() = with(binding.rvDisease) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = diseaseAdapter

        addItemDecoration(
            MarginItemDecoration(
                spaceHeight = resources.getDimension(R.dimen.spacing_16).toInt(),
                startEndHeight = resources.getDimension(R.dimen.spacing_8).toInt()
            )
        )
    }

    private fun initVm() {
        diseaseAdapter.submitList(DummyData.getDummiesDisease())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _dialog = null
    }

    override fun onDiseaseClicked(disease: Disease) = startActivity(
        Intent(this, DetailActivity::class.java).putExtra(
            DetailActivity.DISEASE,
            disease
        )
    )

    private fun showImageResult(state: Boolean) = with(dialog) {
        findViewById<LinearLayout>(R.id.llResult).visibility =
            if (state) View.VISIBLE else View.GONE
        findViewById<TextView>(R.id.tvTitle).text =
            getString(if (state) R.string.picker_repick else R.string.picker_title)
    }

    private fun showLoadingProcessingImage(state: Boolean) = with(dialog) {
        findViewById<LinearLayout>(R.id.llLoading).visibility =
            if (state) View.VISIBLE else View.GONE
        showImageResult(!state)
        findViewById<ConstraintLayout>(R.id.clPicker).visibility =
            if (state) View.GONE else View.VISIBLE
    }

    private fun showDialogDiseasePicker() = with(dialog) {
        setContentView(R.layout.dialog_disease_picker)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        showLoadingProcessingImage(false)
        showImageResult(false)
        setCancelable(true)

        val pickCamera = findViewById<TextView>(R.id.tvCamera)
        val pickGallery = findViewById<TextView>(R.id.tvGallery)

        pickCamera.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay(DISMISS_DELAY)
                this@MainActivity.runOnUiThread { startCameraX() }
            }
        }
        pickGallery.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay(DISMISS_DELAY)
                dismiss()
                this@MainActivity.runOnUiThread { showToast("Clicked Gallery Picker") }
            }
        }

        show()
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        @Suppress("DEPRECATION")
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                it.data?.getSerializableExtra("picture") as File
            }
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = BitmapFactory.decodeFile(myFile?.path)

            showImageResult(true)
            dialog.findViewById<ImageView>(R.id.imgResult).setImageBitmap(result)
            dialog.findViewById<Button>(R.id.btnSubmit).setOnClickListener { processImage() }
        }
    }

    private fun processImage() {
        CoroutineScope(Dispatchers.Main).launch {
            showLoadingProcessingImage(true)
            showToast("Processing IMage")
            dialog.setCancelable(false)
            delay(5000L)
            dialog.dismiss()
        }
    }

    private fun startGallery() {
    }

    private fun startTakePhoto() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showToast("Tidak mendapatkan permission")
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val DISMISS_DELAY = 200L
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}