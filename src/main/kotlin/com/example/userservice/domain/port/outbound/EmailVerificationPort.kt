package com.example.userservice.domain.port.outbound

interface EmailVerificationPort {
    suspend fun verifyEmail(email: String): Boolean
}
