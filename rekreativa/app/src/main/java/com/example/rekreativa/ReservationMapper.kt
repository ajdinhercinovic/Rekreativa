package com.example.rekreativa

fun Reservation.toTermin(): Termin {
    val currentUser = RekreativaData.currentUser

    return Termin(
        id = id,
        tip = tip_dogadjaja,
        terenId = terenId,
        sportId = sportId,
        datumLabel = "$dayOfWeek $date",
        vrijemeLabel = "$timeFrom - $timeTo",
        creator = RekreativaData.userByUsername(userId)?.ime ?: "",
        creator_avatar = RekreativaData.userByUsername(userId)?.avatarRes,
        isCreator = userId == currentUser.username
    )
}
