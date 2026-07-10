package com.example.userservice.infrastructure.adapter.outbound.persistence

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface IUserRepository : CoroutineCrudRepository<UserEntity, Long> {
    suspend fun findByEmail(email: String): UserEntity?
}
