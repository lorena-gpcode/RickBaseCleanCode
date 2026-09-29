package com.edu.rickbasecleancode.domain.repository

import com.edu.rickbasecleancode.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacters(): List<Character>
    //Novo método que recebe o id como parâmetro - dados mokados
    suspend fun getCharacterDetail(id:Int): Character
}
