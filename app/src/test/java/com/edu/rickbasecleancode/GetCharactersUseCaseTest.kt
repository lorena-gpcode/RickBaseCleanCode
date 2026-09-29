package com.edu.rickbasecleancode

import com.edu.rickbasecleancode.domain.model.Character
import com.edu.rickbasecleancode.domain.model.CharacterLocation
import com.edu.rickbasecleancode.domain.repository.CharacterRepository
import com.edu.rickbasecleancode.domain.usecase.GetCharactersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharactersUseCaseTest {

    private val repository: CharacterRepository = mockk()
    private val getCharactersUseCase = GetCharactersUseCase(repository)

    @Test
    fun `deve retornar uma lista de personagens com sucesso quando o repositorio responder`() = runTest {
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
            ),
            Character(
                id = 2,
                name = "Morty Smith",
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

        coEvery { repository.getCharacters() } returns mockCharacters

        // Act
        val result = getCharactersUseCase()

        // Assert
        assertEquals(mockCharacters, result)
        coVerify(exactly = 1) { repository.getCharacters() }
    }
}