package com.iwebsapp.testjusto.model.error

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorApi(var message: String? = null) : Parcelable