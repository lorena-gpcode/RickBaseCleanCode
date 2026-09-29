package com.edu.rickbasecleancode.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.rickbasecleancode.domain.usecase.GetCharacterDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase // Injetado via DI (Koin/Hilt)
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            // 1. Ativa o estado de carregamento
            _uiState.update { it.copy(isLoading = true) }

            try {
                // 2. Chama o Use Case que busca os dados do repositório
                val character = getCharacterDetailUseCase(characterId)

                // 3. Atualiza o estado com os dados reais retornados
                _uiState.update {
                    it.copy(
                        id = character.id,
                        name = character.name,
                        status = character.status,
                        species = character.species, // Ajuste conforme os campos da sua Entidade
                        imageUrl = character.image,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // 4. Em caso de erro, desativa o loading (poderia salvar uma mensagem de erro no estado também)
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}