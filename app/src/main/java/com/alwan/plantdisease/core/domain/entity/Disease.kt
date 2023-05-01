package com.alwan.plantdisease.core.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val id: String,
    val name: String,
    val type: String,
    val imageUrl: String,
    val relatedImageUrl: List<String>,
    val cause: String,
    val countermeasure: String,
) : Parcelable
