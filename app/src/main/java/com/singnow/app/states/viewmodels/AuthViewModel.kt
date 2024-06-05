package com.singnow.app.states.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.singnow.app.configs.Constant
import com.singnow.app.configs.hashPassword
import com.singnow.app.configs.verifyPassword
import com.singnow.app.firebase.FirebaseConfig
import com.singnow.app.states.LoginDto
import com.singnow.app.states.User
import kotlinx.coroutines.launch


class AuthViewModel : ViewModel() {
    private val accountsRef = FirebaseConfig.accountsRef()
    val userCurrent = mutableStateOf<User?>(null)
    val isLoggedIn = mutableStateOf(false)
    private val accountKey = mutableStateOf<String?>(null)
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(Constant.SharedPrefereces.Auth.ROOT, Context.MODE_PRIVATE)
        isLoggedIn.value =
            sharedPreferences.getBoolean(Constant.SharedPrefereces.Auth.IS_LOGGED_IN, false)
        if (isLoggedIn.value) {
            accountKey.value =
                sharedPreferences.getString(Constant.SharedPrefereces.Auth.ACCOUNT_KEY, null)
            loadDataFirst(accountKey.value!!)
        }
    }

    private fun loadDataFirst(accountKey: String) {
        viewModelScope.launch {
            val findByEmail =
                accountsRef.orderByChild(accountKey).limitToFirst(1)
            findByEmail.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val child = snapshot.children.firstOrNull()
                        if (child != null) {
                            val user: User? = child.getValue(User::class.java)

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error(error.message)
                }
            })
        }
    }

    fun logout() {
        sharedPreferences.edit()
            .putBoolean(Constant.SharedPrefereces.Auth.IS_LOGGED_IN, false)
            .putString(Constant.SharedPrefereces.Auth.ACCOUNT_KEY, null)
            .apply()
        isLoggedIn.value = false
        accountKey.value = null
    }

    fun login(
        data: LoginDto,
        startLoading: () -> Unit,
        loginSuccess: () -> Unit,
        loginFailed: () -> Unit,
        emailNotFound: (email: String) -> Unit,
        error: (error: String) -> Unit
    ) {
        viewModelScope.launch {
            startLoading()
            val findByEmail =
                accountsRef.orderByChild("email").equalTo(data.email.trim()).limitToFirst(1)
            findByEmail.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val child = snapshot.children.firstOrNull()
                        if (child != null) {
                            val user: User? = child.getValue(User::class.java)
                            if (user != null) {
                                if (verifyPassword(data.password, user.password)) {
                                    userCurrent.value = user
                                    isLoggedIn.value = true
                                    accountKey.value = child.key
                                    sharedPreferences.edit()
                                        .putBoolean(
                                            Constant.SharedPrefereces.Auth.IS_LOGGED_IN,
                                            true
                                        )
                                        .putString(
                                            Constant.SharedPrefereces.Auth.ACCOUNT_KEY,
                                            accountKey.value
                                        )
                                        .apply()
                                    loginSuccess()
                                } else {
                                    loginFailed()
                                }
                            } else emailNotFound(data.email)
                        } else emailNotFound(data.email)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error(error.message)
                }
            })
        }
    }

    fun register(
        data: User,
        error: (error: String) -> Unit,
        success: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val passwordHashed = hashPassword(data.password)
                data.password = passwordHashed
                val key = accountsRef.push().key
                if (key != null) {
                    accountsRef.child(key).setValue(data)
                }
                success()
            } catch (e: Exception) {
                error(e.message.toString())
            }
        }
    }
}