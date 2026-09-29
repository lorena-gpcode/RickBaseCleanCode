package com.edu.rickbasecleancode.data.model


import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("info")
    val info: InfoResponse,
    @SerializedName("results")
    val results: List<CharacterResponse>
)

data class InfoResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)