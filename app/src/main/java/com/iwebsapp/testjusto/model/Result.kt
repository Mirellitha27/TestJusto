package com.iwebsapp.testjusto.model

data class Result(
    var gender: String? = null,
    var name: Name,
    var location: Location,
    var email: String,
    var phone: String,
    var cell: String,
    var picture: Picture
)