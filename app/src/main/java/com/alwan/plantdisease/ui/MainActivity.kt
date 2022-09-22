package com.alwan.plantdisease.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.plantdisease.R
import com.alwan.plantdisease.databinding.ActivityMainBinding
import com.alwan.plantdisease.domain.entity.Disease
import com.alwan.plantdisease.ui.detail.DetailActivity
import com.alwan.plantdisease.util.DummyData
import com.alwan.plantdisease.util.MarginItemDecoration
import com.alwan.plantdisease.util.showToast

class MainActivity : AppCompatActivity(), DiseaseAdapter.DiseaseCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val diseaseAdapter = DiseaseAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    override fun onDiseaseClicked(disease: Disease) = startActivity(
        Intent(this, DetailActivity::class.java).putExtra(
            DetailActivity.DISEASE,
            disease
        )
    )

}