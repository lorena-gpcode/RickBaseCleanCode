package com.edu.rickbasecleancode.presentation.details

import org.jetbrains.annotations.ApiStatus

data class DetailUiState(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = false
)