package com.example.rekreativa.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rekreativa.Field
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach

data class TereniUiState(
    val isLoading: Boolean = true,
    val city: String? = null,
    val fields: List<Field> = emptyList(),
    val error: String? = null
)

class TereniViewModel(
    private val userRepo: UserRepository,
    private val fieldsRepo: FieldsRepository
) : ViewModel() {

    private val selectedSportId = MutableStateFlow<String?>(null)

    private val _state = MutableStateFlow(TereniUiState(isLoading = true))
    val state: StateFlow<TereniUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userRepo.selectedCityFlow(),
                selectedSportId
            ) { city, sport ->
                city to sport
            }
                .onEach { (city, _) ->
                    // čim dođe city promjena, osvježi UI state
                    _state.update {
                        it.copy(
                            city = city,
                            isLoading = city != null, // ako nema grada, nema loadanja terena
                            error = null,
                            fields = if (city == null) emptyList() else it.fields
                        )
                    }
                }
                .flatMapLatest { (city, sport) ->
                    if (city.isNullOrBlank()) {
                        flowOf(emptyList())
                    } else {
                        fieldsRepo.fieldsFlow(city, sport)
                    }
                }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Greška pri učitavanju",
                            fields = emptyList()
                        )
                    }
                }
                .collect { list ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            fields = list
                        )
                    }
                }
        }
    }

    fun setSportFilter(sportId: String?) {
        selectedSportId.value = sportId
    }

    fun setCity(city: String) {
        // odmah promijeni UI da ne stoji stari grad
        _state.update { it.copy(city = city, isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                userRepo.setSelectedCity(city)
                // snapshot listener će potvrditi novu vrijednost
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message ?: "Ne mogu promijeniti grad")
                }
            }
        }
    }
}

class TerenDetailsViewModel(
    private val fieldsRepo: FieldsRepository,
    terenId: String
) : ViewModel() {

    private val _state = MutableStateFlow<Field?>(null)
    val state: StateFlow<Field?> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fieldsRepo.fieldByIdFlow(terenId)
                .catch { _state.value = null }
                .collect { field ->
                    _state.value = field
                }
        }
    }
}