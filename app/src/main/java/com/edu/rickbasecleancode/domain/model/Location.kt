package com.edu.rickbasecleancode.domain.model

import androidx.annotation.Dimension

data class Location(
    val ind: Int,
    val name:String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String

)