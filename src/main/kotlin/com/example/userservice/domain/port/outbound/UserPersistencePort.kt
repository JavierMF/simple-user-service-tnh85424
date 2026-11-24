package com.example.userservice.domain.port.outbound

import com.example.userservice.domain.model.User

interface UserPersistencePort {
    suspend fun save(user: User): User
    suspend fun findById(id: Long): User?
    suspend fun findAll(): List<User>
    suspend fun deleteById(id: Long)
    suspend fun existsByEmail(email: String): Boolean
}
