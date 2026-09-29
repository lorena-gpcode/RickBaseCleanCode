package com.edu.rickbasecleancode.data.datasource

import com.edu.rickbasecleancode.data.model.CharacterListResponse
import com.edu.rickbasecleancode.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiDataSource {

    @GET("character")
    suspend fun getCharacters(
        @Query("page")page:Int?=null
    ): CharacterListResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int): CharacterResponse

}