package com.example.userservice.application.dto

import java.time.LocalDateTime

data class UserResponse(
    val id: Long?,
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,
    val createdAt: LocalDateTime
)
