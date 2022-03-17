package com.iwebsapp.testjusto.model.error

import android.os.Parcelable
import com.iwebsapp.testjusto.model.error.GeneralError
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorResponse(var error: GeneralError = GeneralError()) : Parcelable