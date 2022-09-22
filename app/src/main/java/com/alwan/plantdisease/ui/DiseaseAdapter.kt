package com.alwan.plantdisease.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alwan.plantdisease.databinding.ItemDiseaseBinding
import com.alwan.plantdisease.domain.entity.Disease
import com.alwan.plantdisease.util.loadImage

class DiseaseAdapter(private val callback: DiseaseCallback) :
    ListAdapter<Disease, DiseaseAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface DiseaseCallback {
        fun onDiseaseClicked(disease: Disease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDiseaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Disease) = with(binding) {
            imgDisease.loadImage(item.imageUrl)
            tvDisease.text = item.name
            tvDiseaseLabel.text = item.type

            root.setOnClickListener { callback.onDiseaseClicked(item) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Disease>() {
            override fun areItemsTheSame(oldItem: Disease, newItem: Disease): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Disease, newItem: Disease): Boolean {
                return oldItem == newItem
            }
        }
    }
}