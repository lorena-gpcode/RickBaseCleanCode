package com.edu.rickbasecleancode.domain.model

data class Episode(
    val id:Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val character: List<String>,
    val url: String,
    val created: String
)