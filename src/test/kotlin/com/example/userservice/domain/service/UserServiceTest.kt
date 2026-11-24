package com.example.userservice.domain.service

import com.example.userservice.domain.model.User
import com.example.userservice.domain.port.outbound.EmailVerificationPort
import com.example.userservice.domain.port.outbound.UserPersistencePort
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserServiceTest {

    private lateinit var userPersistencePort: UserPersistencePort
    private lateinit var emailVerificationPort: EmailVerificationPort
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userPersistencePort = mockk()
        emailVerificationPort = mockk()
        userService = UserService(userPersistencePort, emailVerificationPort)
    }

    @Test
    fun `should create user successfully`() = runTest {
        val user = User(
            username = "johndoe",
            email = "john@example.com",
            password = "password123",
            full_name = "John Doe"
        )

        val savedUser = user.copy(id = 1L)

        coEvery { userPersistencePort.existsByEmail(user.email) } returns false
        coEvery { emailVerificationPort.verifyEmail(user.email) } returns true
        coEvery { userPersistencePort.save(user) } returns savedUser

        val result = userService.createUser(user)

        assertEquals(1L, result.id)
        assertEquals("johndoe", result.username)
        coVerify { userPersistencePort.save(user) }
    }

    @Test
    fun `should throw exception when email already exists`() = runTest {
        val user = User(
            username = "johndoe",
            email = "john@example.com",
            password = "password123",
            full_name = "John Doe"
        )

        coEvery { userPersistencePort.existsByEmail(user.email) } returns true

        assertThrows(IllegalStateException::class.java) {
            kotlinx.coroutines.runBlocking {
                userService.createUser(user)
            }
        }
    }

    @Test
    fun `should get user by id`() = runTest {
        val user = User(
            id = 1L,
            username = "johndoe",
            email = "john@example.com",
            password = "password123",
            full_name = "John Doe"
        )

        coEvery { userPersistencePort.findById(1L) } returns user

        val result = userService.getUserById(1L)

        assertNotNull(result)
        assertEquals("johndoe", result?.username)
    }

    @Test
    fun `should return null when user not found`() = runTest {
        coEvery { userPersistencePort.findById(99L) } returns null

        val result = userService.getUserById(99L)

        assertNull(result)
    }

    @Test
    fun `should delete user`() = runTest {
        coEvery { userPersistencePort.deleteById(1L) } returns Unit

        userService.deleteUser(1L)

        coVerify { userPersistencePort.deleteById(1L) }
    }

}
