package com.edu.rickbasecleancode.presentation.details

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.model.CharacterLocation
import com.edu.rickbasecleancode.domain.usecase.GetCharacterDetailUseCase
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
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val getCharacterDetailUseCase: GetCharacterDetailUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `quando loadCharacterDetails for chamado com sucesso, deve atualizar o uiState com os dados do personagem`() = runTest {
        // Arrange
        val characterId = 1
        val dummyLocation = CharacterLocation(name = "Earth", url = "")
        val mockCharacter = Character(
            id = characterId,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = dummyLocation,
            location = dummyLocation,
            image = "url_da_imagem",
            episode = listOf("1"),
            url = "",
            created = ""
        )

        coEvery { getCharacterDetailUseCase(characterId) } returns mockCharacter

        // Act
        val viewModel = DetailViewModel(getCharacterDetailUseCase)
        viewModel.loadCharacterDetails(characterId)

        // Avança as corrotinas pendentes no despachante de teste
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isLoading)
        assertEquals(characterId, currentState.id)
        assertEquals("Rick Sanchez", currentState.name)
        assertEquals("Alive", currentState.status)
        assertEquals("Human", currentState.species)
        assertEquals("url_da_imagem", currentState.imageUrl)
    }

    @Test
    fun `quando loadCharacterDetails falhar, deve desativar o loading no uiState`() = runTest {
        // Arrange
        val characterId = 1
        coEvery { getCharacterDetailUseCase(characterId) } throws RuntimeException("Erro ao buscar detalhes")

        // Act
        val viewModel = DetailViewModel(getCharacterDetailUseCase)
        viewModel.loadCharacterDetails(characterId)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isLoading)
    }
}