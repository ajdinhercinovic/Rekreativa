package com.example.rekreativa.data

import com.example.rekreativa.Field
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.remove

class UserRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private fun userDoc() = db.collection("users").document(auth.currentUser!!.uid)

    fun selectedCityFlow(): Flow<String?> = callbackFlow {
        val reg = userDoc().addSnapshotListener { snap, err ->
            if (err != null) {
                trySend(null)
                return@addSnapshotListener
            }
            trySend(snap?.getString("selectedCity"))
        }
        awaitClose { reg.remove() }
    }

    suspend fun setSelectedCity(city: String) {
        userDoc().set(mapOf("selectedCity" to city), SetOptions.merge()).await()
    }
}

class FieldsRepository(
    private val db: FirebaseFirestore
) {
    fun fieldsFlow(city: String, sportId: String?): Flow<List<Field>> = callbackFlow {
        var q: Query = db.collection("fields")
            .whereEqualTo("city", city)
            .whereEqualTo("isActive", true)

        if (!sportId.isNullOrBlank()) {
            q = q.whereArrayContains("sportIds", sportId)
        }

        val reg = q.addSnapshotListener { snap, err ->
            if (err != null) {
                close(err)
                return@addSnapshotListener
            }
            val items = snap?.documents.orEmpty().map { d ->
                Field(
                    id = d.id,
                    name = d.getString("name") ?: "",
                    city = d.getString("city") ?: "",
                    sportIds = (d.get("sportIds") as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                    recommendedPeople = d.getString("recommendedPeople") ?: "",
                    imageUrl = d.getString("imageUrl") ?: "",
                    isActive = d.getBoolean("isActive") ?: true
                )
            }
            trySend(items)
        }

        awaitClose { reg.remove() }
    }

    fun fieldByIdFlow(id: String): Flow<Field?> = callbackFlow {
        val reg = db.collection("fields")
            .document(id)
            .addSnapshotListener { snap, err ->

                if (err != null) {
                    close(err)
                    return@addSnapshotListener
                }

                if (snap != null && snap.exists()) {
                    val field = Field(
                        id = snap.id,
                        name = snap.getString("name") ?: "",
                        city = snap.getString("city") ?: "",
                        sportIds = (snap.get("sportIds") as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                        recommendedPeople = snap.getString("recommendedPeople") ?: "",
                        imageUrl = snap.getString("imageUrl") ?: "",
                        isActive = snap.getBoolean("isActive") ?: true,
                        pricePerHour = snap.getString("pricePerHour") ?: ""
                    )
                    trySend(field)
                } else {
                    trySend(null)
                }
            }

        awaitClose { reg.remove() }
    }
}

