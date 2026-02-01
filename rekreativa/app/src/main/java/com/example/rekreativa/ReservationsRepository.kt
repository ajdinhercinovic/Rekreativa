package com.example.rekreativa

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReservationsRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val col = db.collection("reservations")

    suspend fun createReservation(reservation: Reservation): String{
        val doc = col.document()
        val data = reservation.copy(id = doc.id)
        doc.set(data).await()
        return doc.id
    }

    suspend fun getReservationsForUser(userId: String): List<Reservation>{
        val snap = col
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snap.documents.mapNotNull { it.toObject(Reservation::class.java) }
    }

    suspend fun updateStatus(id: String, status:String){
        col.document(id).update("status",status).await()
    }

    suspend fun deleteReservation(id: String){
        col.document(id).delete().await()
    }
}
