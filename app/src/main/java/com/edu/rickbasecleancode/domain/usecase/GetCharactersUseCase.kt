package com.edu.rickbasecleancode.domain.usecase

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): List<Character> {
        return repository.getCharacters()
    }
}
