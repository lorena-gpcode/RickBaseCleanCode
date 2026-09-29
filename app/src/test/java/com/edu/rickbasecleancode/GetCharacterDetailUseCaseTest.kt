package com.edu.rickbasecleancode

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.model.CharacterLocation
import com.edu.rickbasecleancode.domain.repository.CharacterRepository
import com.edu.rickbasecleancode.domain.usecase.GetCharacterDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharacterDetailUseCaseTest {

    private val repository: CharacterRepository = mockk()
    private val getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)

    @Test
    fun `deve retornar os detalhes de um personagem com sucesso quando fornecido um id valido`() = runTest {
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
            image = "",
            episode = listOf("1"),
            url = "",
            created = ""
        )

        // Configuramos o mock para retornar o personagem específico para o id 1
        coEvery { repository.getCharacterDetail(characterId) } returns mockCharacter

        // Act
        val result = getCharacterDetailUseCase(characterId)

        // Assert
        assertEquals(mockCharacter, result)

        // Garantimos que o repositório foi chamado com o id correto exatamente uma vez
        coVerify(exactly = 1) { repository.getCharacterDetail(characterId) }
    }
}