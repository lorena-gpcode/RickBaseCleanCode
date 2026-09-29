package com.edu.rickbasecleancode.data.datasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
        val api: CharacterApiDataSource by lazy {
            retrofit.create(CharacterApiDataSource::class.java)
        }

}