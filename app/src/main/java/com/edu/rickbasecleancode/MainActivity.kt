package com.edu.rickbasecleancode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.edu.rickbasecleancode.data.datasource.RetrofitInstance
import com.edu.rickbasecleancode.data.repository.CharacterRepositoryImpl
import com.edu.rickbasecleancode.domain.usecase.GetCharacterDetailUseCase
import com.edu.rickbasecleancode.domain.usecase.GetCharactersUseCase
import com.edu.rickbasecleancode.presentation.details.DetailScreen
import com.edu.rickbasecleancode.presentation.details.DetailViewModel
import com.edu.rickbasecleancode.presentation.home.HomeScreen
import com.edu.rickbasecleancode.presentation.home.HomeViewModel
import com.edu.rickbasecleancode.ui.theme.RickBaseCleanCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiDataSource = RetrofitInstance.api
        val repository = CharacterRepositoryImpl(apiDataSource)
        
        val getCharactersUseCase = GetCharactersUseCase(repository)
        val homeViewModel = HomeViewModel(getCharactersUseCase)

        val getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)
        val detailViewModel = DetailViewModel(getCharacterDetailUseCase)

        setContent {
            RickBaseCleanCodeTheme {
                var selectedCharacterId by remember { mutableStateOf<Int?>(null) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val currentId = selectedCharacterId
                    if (currentId != null) {
                        BackHandler {
                            selectedCharacterId = null
                        }
                        
                        DetailScreen(
                            characterId = currentId,
                            viewModel = detailViewModel,
                            modifier = Modifier.padding(innerPadding),
                            onBackClick = { selectedCharacterId = null }
                        )
                    } else {
                        HomeScreen(
                            viewModel = homeViewModel,
                            modifier = Modifier.padding(innerPadding),
                            onCharacterClick = { id ->
                                selectedCharacterId = id
                            }
                        )
                    }
                }
            }
        }
    }
}
