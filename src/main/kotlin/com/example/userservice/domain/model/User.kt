package com.example.userservice.domain.model

import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String,
    val full_name: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
