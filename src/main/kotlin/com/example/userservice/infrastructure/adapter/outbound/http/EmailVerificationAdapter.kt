package com.example.userservice.infrastructure.adapter.outbound.http

import com.example.userservice.domain.port.outbound.EmailVerificationPort
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

class EmailVerificationAdapter(
    private val webClient: WebClient
) : EmailVerificationPort {

    private val logger = LoggerFactory.getLogger(EmailVerificationAdapter::class.java)

    override suspend fun verifyEmail(email: String): Boolean {
        try {
            val response = webClient
                .get()
                .uri("/api/verify?email=$email")
                .retrieve()
                .awaitBody<EmailVerificationResponse>()

            return response.is_valid
        } catch (e: Exception) {
            logger.debug("Email verification failed for $email: ${e.message}")
            return true
        }
    }
}

data class EmailVerificationResponse(
    val is_valid: Boolean,
    val reason: String?
)
