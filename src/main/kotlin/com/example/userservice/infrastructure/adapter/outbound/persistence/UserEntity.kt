package com.example.userservice.infrastructure.adapter.outbound.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserEntity(
    @Id
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String,
    val fullName: String
)
