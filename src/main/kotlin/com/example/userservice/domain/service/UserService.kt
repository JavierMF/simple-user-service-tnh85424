package com.example.userservice.domain.service

import com.example.userservice.domain.model.User
import com.example.userservice.domain.port.inbound.UserManagementPort
import com.example.userservice.domain.port.outbound.EmailVerificationPort
import com.example.userservice.domain.port.outbound.UserPersistencePort
import com.example.userservice.infrastructure.adapter.outbound.persistence.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

/**
 * Domain service implementing user management use cases.
 */
class UserService(
    private val userPersistencePort: UserPersistencePort,
    private val emailVerificationPort: EmailVerificationPort
) : UserManagementPort {

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    @Transactional
    override suspend fun createUser(user: User): User {
        logger.info("Creating user: $user")

        if (user.email.isEmpty()) {
            throw IllegalArgumentException("Email cannot be empty")
        }

        if (user.username.isEmpty()) {
            throw IllegalArgumentException("Username cannot be empty")
        }

        val emailExists = userPersistencePort.existsByEmail(user.email)
        if (emailExists) {
            throw IllegalStateException("User with email ${user.email} already exists")
        }

        val isValidEmail = emailVerificationPort.verifyEmail(user.email)
        if (!isValidEmail) {
            throw IllegalArgumentException("Email verification failed for ${user.email}")
        }

        return userPersistencePort.save(user)
    }

    override suspend fun getUserById(id: Long): User? = userPersistencePort.findById(id)

    override suspend fun getAllUsers(): List<User> {
        val users = userPersistencePort.findAll()
        logger.debug("Retrieved ${users.size} users from database")
        return users
    }

    override suspend fun deleteUser(id: Long) {
        userPersistencePort.deleteById(id)
    }

    override suspend fun updateUser(id: Long, user: User): User? {
        val existingUser = userPersistencePort.findById(id)
        if (existingUser != null) {
            val updatedUser = existingUser.copy(
                username = user.username,
                email = user.email,
                password = user.password,
                full_name = user.full_name
            )
            return userPersistencePort.save(updatedUser)
        }
        return null
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password,
            fullName = user.full_name
        )
    }
}
