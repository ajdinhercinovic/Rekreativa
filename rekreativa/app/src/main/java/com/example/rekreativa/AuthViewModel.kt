package com.example.rekreativa

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthState(
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)

class AuthViewModel(
    private val authService: AuthService = AuthService()
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun login(email: String, password: String) {
        _state.value = AuthState(loading = true)

        authService.signIn(email, password) { ok, err ->
            _state.value = AuthState(
                loading = false,
                loggedIn = ok,
                error = if (ok) null else (err ?: "Neuspješna prijava.")
            )
        }
    }
}