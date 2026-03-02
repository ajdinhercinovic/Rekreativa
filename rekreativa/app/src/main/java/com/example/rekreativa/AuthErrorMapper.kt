package com.example.rekreativa

import com.google.firebase.auth.FirebaseAuthException

fun FirebaseAuthException.toBosnianMessage(): String {
    return when (this.errorCode) {

        // LOGIN
        "ERROR_INVALID_EMAIL" -> "Email nije ispravno unesen."
        "ERROR_USER_NOT_FOUND" -> "Korisnik sa ovim emailom ne postoji."
        "ERROR_WRONG_PASSWORD" -> "Pogrešna lozinka."
        "ERROR_USER_DISABLED" -> "Ovaj nalog je deaktiviran."

        // REGISTER
        "ERROR_EMAIL_ALREADY_IN_USE" -> "Ovaj email je već registrovan."
        "ERROR_WEAK_PASSWORD" -> "Lozinka je preslaba (minimum 6 karaktera)."

        else -> "Došlo je do greške. Pokušajte ponovo."
    }
}