package com.singnow.app.states

class User(
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String
) {
    constructor() : this("", "", "", "")
}

data class LoginDto(
    val email: String,
    val password: String
)