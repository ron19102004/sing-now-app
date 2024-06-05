package com.singnow.app.firebase

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

object FirebaseConfig{
    private val database: DatabaseReference = Firebase.database.reference
    fun accountsRef(): DatabaseReference {
        return database.child("accounts")
    }
}
