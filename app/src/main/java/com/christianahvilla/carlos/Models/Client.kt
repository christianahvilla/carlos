package com.christianahvilla.carlos.Models

data class Client (
    var id: Int,
    var client: String,
    var domain: String,
    var price: Int,
    var lat: String,
    var lon: String,
    var kind: String
)