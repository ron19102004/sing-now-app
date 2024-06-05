package com.singnow.app.configs

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}
fun verifyPassword(inputPassword: String, storedHash: String): Boolean {
    return BCrypt.checkpw(inputPassword, storedHash)
}
