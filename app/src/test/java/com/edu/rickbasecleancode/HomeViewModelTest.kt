package com.edu.rickbasecleancode.presentation.home

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.model.CharacterLocation
import com.edu.rickbasecleancode.domain.usecase.GetCharactersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun onBefore() {
        // Define o dispatcher principal para testes para evitar erros de Threading do Android
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun onAfter() {
        // Reseta o dispatcher principal após cada teste
        Dispatchers.resetMain()
    }

    @Test
    fun `quando a viewmodel e iniciada com sucesso, deve atualizar o uistate para Success`() = runTest {
        // Arrange
        val dummyLocation = CharacterLocation(name = "Earth", url = "")
        val mockCharacters = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = dummyLocation,
                location = dummyLocation,
                image = "",
                episode = listOf("1"),
                url = "",
                created = ""
            )
        )

        coEvery { getCharactersUseCase() } returns mockCharacters

        // Act - Ao instanciar a ViewModel, o bloco init chama o fetchCharacters()
        val viewModel = HomeViewModel(getCharactersUseCase)

        // Avança a fila de tarefas das corrotinas no dispatcher de teste
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val currentState = viewModel.uistate.value
        assert(currentState is HomeUiState.Success)
        assertEquals(mockCharacters, (currentState as HomeUiState.Success).characters)
    }

    @Test
    fun `quando a viewmodel falha ao buscar personagens, deve atualizar o uistate para Error`() = runTest {
        // Arrange
        val errorMessage = "Erro de rede"
        coEvery { getCharactersUseCase() } throws RuntimeException(errorMessage)

        // Act
        val viewModel = HomeViewModel(getCharactersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val currentState = viewModel.uistate.value
        assert(currentState is HomeUiState.Error)
        assertEquals(errorMessage, (currentState as HomeUiState.Error).message)
    }
}