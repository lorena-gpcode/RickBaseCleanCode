package com.edu.rickbasecleancode.domain.usecase

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.repository.CharacterRepository

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Character {
        return repository.getCharacterDetail(id)
    }
}
