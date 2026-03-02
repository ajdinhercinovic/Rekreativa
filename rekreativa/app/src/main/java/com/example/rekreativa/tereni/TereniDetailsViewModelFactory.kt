package com.example.rekreativa.tereni

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rekreativa.data.FieldsRepository
import com.example.rekreativa.data.TerenDetailsViewModel
import com.google.firebase.firestore.FirebaseFirestore

class TerenDetailsViewModelFactory(
    private val terenId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val firestore = FirebaseFirestore.getInstance()
        val repo = FieldsRepository(firestore)

        return TerenDetailsViewModel(repo, terenId) as T
    }
}