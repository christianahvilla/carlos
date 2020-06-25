package com.christianahvilla.carlos.Models

data class User(
    var id: Int,
    var name: String,
    var email: String,
    var password: String?,
    var logged: Int
)