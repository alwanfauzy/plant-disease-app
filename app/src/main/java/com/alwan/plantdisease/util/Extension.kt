package com.alwan.plantdisease.util

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import com.alwan.plantdisease.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
        .into(this)
}

fun Activity.showToast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
