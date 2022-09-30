package com.alwan.plantdisease.util

import android.app.Activity
import android.app.Application
import android.widget.ImageView
import android.widget.Toast
import com.alwan.plantdisease.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
        .into(this)
}

fun Activity.showToast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Application.createFile(): File {
    val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
        File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else this.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}