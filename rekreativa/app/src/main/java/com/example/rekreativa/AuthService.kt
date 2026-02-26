package com.example.rekreativa

import com.google.firebase.auth.FirebaseAuth

class AuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful, task.exception?.localizedMessage)
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}