package com.guicarneirodev.taxiapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val rating: Float,
    val comment: String
) : Parcelable
