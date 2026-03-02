package com.example.rekreativa.tereni

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rekreativa.data.FieldsRepository
import com.example.rekreativa.data.TereniViewModel
import com.example.rekreativa.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TereniViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        val userRepo = UserRepository(firestore, auth)
        val fieldsRepo = FieldsRepository(firestore)

        return TereniViewModel(userRepo, fieldsRepo) as T
    }
}