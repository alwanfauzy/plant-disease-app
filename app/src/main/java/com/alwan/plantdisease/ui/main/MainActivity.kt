package com.alwan.plantdisease.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.plantdisease.R
import com.alwan.plantdisease.core.data.Resource
import com.alwan.plantdisease.core.domain.entity.Disease
import com.alwan.plantdisease.core.domain.entity.WeatherInfo
import com.alwan.plantdisease.databinding.ActivityMainBinding
import com.alwan.plantdisease.ui.DiseaseAdapter
import com.alwan.plantdisease.ui.detail.DetailActivity
import com.alwan.plantdisease.util.Data
import com.alwan.plantdisease.util.MarginItemDecoration
import com.alwan.plantdisease.util.showToast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DiseaseAdapter.DiseaseCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val diseaseAdapter = DiseaseAdapter(this)
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        initVm()
        initView()
        initWeather()

    }

    private fun initWeather() {
        setLastKnowLocation()

        weatherViewModel.weather.observe(this) { res ->
            when (res) {
                is Resource.Loading -> showLoadingWeather(true)
                is Resource.Success -> {
                    showLoadingWeather(false)
                    populateWeatherInfo(res.data)
                }
                is Resource.Error -> showToast(res.message)
            }
        }

        weatherViewModel.city.observe(this) {
            binding.tvLocation.text = it
        }
    }

    private fun populateWeatherInfo(info: WeatherInfo?) = with(binding) {
        tvTemp.text = info?.temperature.toString()
        tvRain.text = info?.clouds.toString()
        tvHumid.text = info?.humidity.toString()
        tvWind.text = info?.windSpeed.toString()
    }

    private fun showLoadingWeather(state: Boolean) = with(binding) {
        llWeatherLoading.visibility = if (state) View.VISIBLE else View.GONE
        clWeatherInfo.visibility = if (state) View.INVISIBLE else View.VISIBLE
    }

    private fun initView() = with(binding) {
        tvWelcome.text = resources.getString(R.string.welcome_text, "User")
        tvTemp.text = resources.getString(R.string.temp_value, "32.8")
        tvHumid.text = resources.getString(R.string.humid_value, "60")
        tvRain.text = resources.getString(R.string.rain_value, "4.2")
        tvWind.text = resources.getString(R.string.wind_value, "3.9")
        initRv()

        cvDiagnose.setOnClickListener {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    REQUEST_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                showDialogDiseasePicker()
            }
        }

        tvLocation.setOnClickListener {
            setLastKnowLocation()
        }
    }


    @SuppressLint("MissingPermission")
    private fun setLastKnowLocation() {
        if (!allPermissionsGranted())
            ActivityCompat.requestPermissions(
                this@MainActivity,
                REQUEST_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        else
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location == null) {
                    showLocationPrompt()
                    return@addOnSuccessListener
                }

                weatherViewModel.setCity(
                    getCurrentCity(
                        location.latitude,
                        location.longitude
                    )
                )
                weatherViewModel.setLocation(location)
            }
    }

    private fun getCurrentCity(lat: Double, long: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(lat, long, 1)

        return addresses?.get(0)?.locality.orEmpty()
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
        diseaseAdapter.submitList(Data.getPlantDisease())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDiseaseClicked(disease: Disease) = startActivity(
        Intent(this, DetailActivity::class.java).putExtra(
            DetailActivity.DISEASE,
            disease
        )
    )

    private fun showDialogDiseasePicker() {
        val dialogDiseasePicker = DiseasePickerDialogFragment()

        dialogDiseasePicker.show(supportFragmentManager, DIALOG_DISEASE_PICKER_TAG)
    }

    private fun showLocationPrompt() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            // Cast to a resolvable exception.
                            val resolvable: ResolvableApiException =
                                exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                this, LocationRequest.PRIORITY_HIGH_ACCURACY
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.

                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showToast(getString(R.string.need_permission))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LocationRequest.PRIORITY_HIGH_ACCURACY -> {
                if (resultCode != Activity.RESULT_OK)
                    showToast("Enable your GPS to get weather information")
            }
        }
    }

    private fun allPermissionsGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val DIALOG_DISEASE_PICKER_TAG = "dialog_disease_picker"
        private val REQUEST_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val CAMERA_X_RESULT = 200
    }
}