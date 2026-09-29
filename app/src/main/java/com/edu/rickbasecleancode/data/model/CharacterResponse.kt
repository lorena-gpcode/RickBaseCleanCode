package com.edu.rickbasecleancode.data.model

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.model.CharacterLocation
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: CharacterLocationResponse,
    @SerializedName("location")
    val location: CharacterLocationResponse,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

data class CharacterLocationResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

fun CharacterLocationResponse.toDomain(): CharacterLocation {
    return CharacterLocation(
        name = this.name,
        url = this.url
    )
}

fun CharacterResponse.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.origin.toDomain(),
        location = this.location.toDomain(),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}
