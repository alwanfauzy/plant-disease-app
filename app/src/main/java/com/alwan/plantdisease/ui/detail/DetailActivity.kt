package com.alwan.plantdisease.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.plantdisease.R
import com.alwan.plantdisease.databinding.ActivityDetailBinding
import com.alwan.plantdisease.core.domain.entity.weather.Disease
import com.alwan.plantdisease.util.MarginItemDecoration
import com.alwan.plantdisease.util.loadImage
import com.alwan.plantdisease.util.showToast

class DetailActivity : AppCompatActivity(), RelatedImagesAdapter.RelatedImageCallback {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val relatedImagesAdapter = RelatedImagesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val disease = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DISEASE, Disease::class.java)
        } else {
            intent.getParcelableExtra(DISEASE)
        }

        Log.d("DISEASE", disease?.name.toString())

        populateDetail(disease)
    }

    private fun setupToolbar() = binding.toolbarDetail.apply {
        setSupportActionBar(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(this)
    }

    private fun populateDetail(item: Disease?) = with(binding) {
        toolbarDetail.title = item?.name
        tvCause.text = item?.cause
        tvCountermeasure.text = item?.countermeasure
        imgCoverDetail.loadImage(item?.imageUrl)

        initRelatedImagesRv()
        relatedImagesAdapter.submitList(item?.relatedImageUrl)
    }

    private fun initRelatedImagesRv() = with(binding.rvRelatedImages) {
        layoutManager =
            LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        adapter = relatedImagesAdapter

        val cellMargin = MarginItemDecoration(
            spaceHeight = resources.getDimension(R.dimen.spacing_8).toInt(),
            startEndHeight = resources.getDimension(R.dimen.spacing_24).toInt(),
            isHorizontal = true
        )
        addItemDecoration(cellMargin)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRelatedImageClicked(image: String) {
        showToast("$image is Clicked")
    }

    companion object {
        const val DISEASE = "disease"
    }
}