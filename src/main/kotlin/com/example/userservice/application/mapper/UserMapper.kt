package com.example.userservice.application.mapper

import com.example.userservice.application.dto.CreateUserRequest
import com.example.userservice.application.dto.UserResponse
import com.example.userservice.domain.model.User

object UserMapper {

    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password,
            fullName = user.full_name,
            createdAt = user.createdAt
        )
    }

    fun toDomain(request: CreateUserRequest): User {
        return User(
            username = request.username,
            email = request.email,
            password = request.password,
            full_name = request.full_name
        )
    }
}
