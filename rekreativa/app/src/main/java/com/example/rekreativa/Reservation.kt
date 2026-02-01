package com.example.rekreativa

data class Reservation(
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
)