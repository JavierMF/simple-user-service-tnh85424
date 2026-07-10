package com.example.userservice.application.dto

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val full_name: String = ""
)
