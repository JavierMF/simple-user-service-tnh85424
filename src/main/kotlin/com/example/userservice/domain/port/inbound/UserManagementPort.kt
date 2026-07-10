package com.example.userservice.domain.port.inbound

import com.example.userservice.domain.model.User

interface UserManagementPort {
    suspend fun createUser(user: User): User
    suspend fun getUserById(id: Long): User?
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(id: Long)
    suspend fun updateUser(id: Long, user: User): User?
}
