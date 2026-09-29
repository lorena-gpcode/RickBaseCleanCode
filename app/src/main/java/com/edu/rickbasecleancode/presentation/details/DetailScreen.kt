package com.edu.rickbasecleancode.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.edu.rickbasecleancode.presentation.components.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    characterId: Int,
    viewModel: DetailViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    // Carrega os detalhes do personagem ao entrar na tela ou mudar o ID
    LaunchedEffect(characterId) {
        viewModel.loadCharacterDetails(characterId)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = if (state.name.isEmpty()) "Detalhes do Personagem" else state.name,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = state.imageUrl,
                        contentDescription = state.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = state.name.ifEmpty { "Nome Indisponível" },
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Status: ${state.status.ifEmpty { "Desconhecido" }}")
                            Text(text = "Espécie: ${state.species.ifEmpty { "Desconhecida" }}")
                        }
                    }
                }
            }
        }
    }
}
