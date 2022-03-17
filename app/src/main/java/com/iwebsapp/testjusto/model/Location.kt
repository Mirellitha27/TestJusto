package com.iwebsapp.testjusto.model

data class Location(
    var street: Street,
    var city: String,
    var state: String,
    var country: String,
    var postcode: Int
)