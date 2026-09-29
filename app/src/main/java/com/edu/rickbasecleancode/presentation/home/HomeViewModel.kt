package com.edu.rickbasecleancode.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.rickbasecleancode.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uistate = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uistate: StateFlow<HomeUiState> = _uistate.asStateFlow()

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _uistate.value = HomeUiState.Loading
            try {
                val result = getCharactersUseCase()
                _uistate.value = HomeUiState.Success(result)
            }catch (e: Exception){
                _uistate.value = HomeUiState.Error(e.localizedMessage?: "Erro desconhecido")
            }
        }
    }


}
