package com.alwan.plantdisease.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alwan.plantdisease.databinding.ItemRelatedImageBinding
import com.alwan.plantdisease.util.loadImage

class RelatedImagesAdapter(private val callback: RelatedImageCallback) :
    ListAdapter<String, RelatedImagesAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface RelatedImageCallback {
        fun onRelatedImageClicked(image: String)
    }

    inner class ViewHolder(private val binding: ItemRelatedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.imgRelatedImage.loadImage(item)

            binding.root.setOnClickListener { callback.onRelatedImageClicked(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRelatedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}