package com.example.userservice.infrastructure.adapter.outbound.persistence

import com.example.userservice.domain.model.User
import com.example.userservice.domain.port.outbound.UserPersistencePort
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class UserPersistenceAdapter(
    private val userRepository: IUserRepository
) : UserPersistencePort {

    private val logger = LoggerFactory.getLogger(UserPersistenceAdapter::class.java)

    override suspend fun save(user: User): User {
        val entity = UserEntity(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password,
            fullName = user.full_name
        )
        val savedEntity = userRepository.save(entity)
        return toDomain(savedEntity)
    }

    override suspend fun findById(id: Long): User? {
        val entity = runCatching {
            userRepository.findById(id)
        }.getOrNull()

        return entity?.let { toDomain(it) }
    }

    override suspend fun findAll(): List<User> {
        return runBlocking {
            userRepository.findAll().toList().map { toDomain(it) }
        }
    }

    override suspend fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    override suspend fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    private fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            email = entity.email,
            password = entity.password,
            full_name = entity.fullName,
            createdAt = LocalDateTime.now()
        )
    }
}
