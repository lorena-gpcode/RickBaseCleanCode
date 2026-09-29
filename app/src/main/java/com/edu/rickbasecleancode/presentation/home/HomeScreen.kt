package com.edu.rickbasecleancode.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.edu.rickbasecleancode.presentation.components.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier, // padrão de projeto responsavel pela responsividade das telas
    onCharacterClick: (Int) -> Unit = {},
) {
    //Gestão de estados

    val state by viewModel.uistate.collectAsState() // recebe das telas os padroes de estado
    var isSearchActive by remember { mutableStateOf(false) } // gestao de estado da barra de pesquisa
    var searchQuery by remember { mutableStateOf("") } // gestao de estado da consulta, ela verifica o que o usuario digita
    //na consulta e adequa no campo

    Scaffold( // define o esqueleto , a estrutura da tela
        topBar = { // insere a barra de top da tela
            AppToolbar(
                title = "Api - Rick & Morty",
                actionIcon = Icons.Default.Search,
                onActionClick = {
                    isSearchActive = true
                },
                logo = {//Espaçamento interno calculado automaticamente pelo
                    // Scaffold para evitar que o conteúdo da tela fique escondido debaixo da barra superior.
                    AsyncImage(
                        model = "https://www.freepnglogos.com/uploads/rick-and-morty-png/rick-and-morty-portal-shoes-white-clothing-zavvi-23.png",
                        contentDescription = "Rick and Morty Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 8.dp)
                    )
                },
                isSearchActive = isSearchActive,
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    searchQuery = query
                },
                onCloseSearchClick = {
                    isSearchActive = false
                    searchQuery = ""
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
       Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = state) {
                is HomeUiState.Loading -> {//Se os dados ainda estiverem
                    // a ser buscados, exibe um indicador circular de progresso
                    // (rodinha de carregamento) centralizado na tela.

                    CircularProgressIndicator()
                }
                is HomeUiState.Success -> {
                    val filteredCharacters = if (searchQuery.isEmpty()) {
                        currentState.characters
                    } else {
                        currentState.characters.filter {
                            it.name.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    if (filteredCharacters.isEmpty()) {
                        Text(text = "Nenhum personagem encontrado")
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredCharacters) { character ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onCharacterClick(character.id) }
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = character.image,
                                        contentDescription = character.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                    )

                                    Text(
                                        text = character.name,
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
                //Se ocorrer alguma falha ao buscar os dados na API,
                // o estado muda para Error e exibe uma mensagem de texto vermelha ou padrão indicando o erro ocorrido.
                is HomeUiState.Error -> {
                    Text(text = "Erro: ${currentState.message}")
                }
            }
        }
    }
}
