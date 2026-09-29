package com.edu.rickbasecleancode.data.repository

import com.edu.rickbasecleancode.data.datasource.CharacterApiDataSource
import com.edu.rickbasecleancode.data.model.toDomain
import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val apiDataSource: CharacterApiDataSource
) : CharacterRepository{

    override suspend fun getCharacters(): List<Character> {
        val response = apiDataSource.getCharacters()

        return response.results.map {
            it.toDomain()
        }
    }

    override suspend fun getCharacterDetail(id: Int): Character {
        val response = apiDataSource.getCharacterById(id)
        return response.toDomain()
    }
}
