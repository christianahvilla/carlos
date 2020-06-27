package com.christianahvilla.carlos.Models

data class Client (
    var id: Int,
    var client: String,
    var domain: String,
    var price: Int,
    var street: String,
    var number: String,
    var neighborhood: String,
    var state: String,
    var zipCode: String,
    var kind: String
)