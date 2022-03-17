package com.iwebsapp.testjusto.model.error

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeneralError(
    var status: Int = 0,
    var code: Int = 302,
    var message: String = "El servicio no está disponible, por favor intente más tarde"
) : Parcelable