package com.example.userservice.infrastructure.adapter.inbound.rest

import com.example.userservice.application.dto.CreateUserRequest
import com.example.userservice.application.dto.UserResponse
import com.example.userservice.application.mapper.UserMapper
import com.example.userservice.domain.port.inbound.UserManagementPort
import com.example.userservice.infrastructure.adapter.outbound.persistence.UserPersistenceAdapter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userManagementPort: UserManagementPort,
    private val userPersistenceAdapter: UserPersistenceAdapter
) {

    @PostMapping
    suspend fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = UserMapper.toDomain(request)
        val createdUser = userManagementPort.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(createdUser))
    }

    @GetMapping("/{id}")
    suspend fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userPersistenceAdapter.findById(id)
        return if (user != null) {
            ResponseEntity.ok(UserMapper.toResponse(user))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    suspend fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userManagementPort.getAllUsers()
        return ResponseEntity.ok(users.map { UserMapper.toResponse(it) })
    }

    @PutMapping("/{id}")
    suspend fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: CreateUserRequest
    ): ResponseEntity<UserResponse> {
        val user = UserMapper.toDomain(request)
        val updatedUser = userManagementPort.updateUser(id, user)
        return if (updatedUser != null) {
            ResponseEntity.ok(UserMapper.toResponse(updatedUser))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    suspend fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userManagementPort.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
