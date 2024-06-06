package com.singnow.app.states

import kotlinx.serialization.Serializable

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

class Video(
    var key: String,
    var image: String,
    var title: String,
    var description: String,
    var url: String
){
    constructor() : this("", "", "", "", "")
}

class VideoResponse(
    var image: String,
    var title: String,
    var description: String,
    var url: String
) {
    constructor() : this("", "", "", "")
}