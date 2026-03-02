package com.example.rekreativa

import androidx.annotation.DrawableRes

data class Sport(
    val id: String,
    val naziv: String,
    @DrawableRes val iconRes: Int
)

data class Field(
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val sportIds: List<String> = emptyList(),
    val recommendedPeople: String = "",
    val imageUrl: String = "",
    val isActive: Boolean = true,
    val pricePerHour: String = ""
)

data class Teren(
    val id: String,
    val naziv: String,
    val opis: String,
    @DrawableRes val slikaRes: Int,
    val sportIds: List<String>,
    val cijenaPoSatu: String,
    val preporucenoOsoba: String
)

/*data class Reservation(
    val id: String = "",
    val userId: String = "",
    val terenId: String = "",
    val terenName: String = "",
    val sportId: String = "",
    val date: String = "",
    val dayOfWeek: String = "",
    val timeFrom: String = "",
    val timeTo: String = "",
    val tip_dogadjaja: String = "",
    val tip_rezervacije: String = "",
    val status: String = "PENDING"
)*/
enum class DogadjajTip { REKREACIJA, TRENING, TURNIR}
enum class PristupTip { OTVOREN, ZAHTJEV }

data class Dogadjaj(
    val id: String,
    val tip: DogadjajTip,
    val sportId: String,
    val lokacijaTerenId: String,
    val datumLabel: String,
    val vrijemeLabel: String,
    val maxUcesnika: Int,
    val prijavljeno: Int,
    val pristup: PristupTip,
    val organizatorIme: String,
    @DrawableRes val organizatorAvatarRes: Int? = null
){
    val slobodnihMjesta: Int get() = (maxUcesnika - prijavljeno).coerceAtLeast(0)
}

data class Termin(
    val id: String,
    val tip: String,
    val terenId: String,
    val sportId: String,
    val datumLabel: String,
    val vrijemeLabel: String,
    val creator: String,
    @DrawableRes val creator_avatar: Int? = null,
    val isCreator: Boolean
)

enum class PozivTip {
    PRIMLJEN,
    POSLAN
}

data class Poziv(
    val id: String,
    val tip: PozivTip,
    val terenId: String,
    val sportId: String,
    val datumLabel: String,
    val vrijemeLabel: String,
    val korisnikIme: String,
    @DrawableRes val korisnikAvatarRes: Int? = null
)

enum class TerminiFilter {
    SVE, TERMINI, POZIVI
}

data class User(
    val username: String,
    val ime: String,
    val prezime: String,
    val email: String,
    val datumRodjenja: String,
    @DrawableRes val avatarRes: Int
)

data class SportStat(
    @DrawableRes val sportIconRes: Int,
    val brojTermina: Int,
    val fairPlay: Double,
    val znanje: Double
)