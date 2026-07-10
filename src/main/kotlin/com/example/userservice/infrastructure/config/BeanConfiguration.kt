package com.example.userservice.infrastructure.config

import com.example.userservice.domain.port.outbound.EmailVerificationPort
import com.example.userservice.domain.port.outbound.UserPersistencePort
import com.example.userservice.domain.service.UserService
import com.example.userservice.infrastructure.adapter.outbound.http.EmailVerificationAdapter
import com.example.userservice.infrastructure.adapter.outbound.persistence.IUserRepository
import com.example.userservice.infrastructure.adapter.outbound.persistence.UserPersistenceAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class BeanConfiguration {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://email-verification-service:8081")
            .build()
    }

    @Bean
    fun emailVerificationPort(webClient: WebClient): EmailVerificationPort {
        return EmailVerificationAdapter(webClient)
    }

    @Bean
    fun userPersistencePort(userRepository: IUserRepository): UserPersistencePort {
        return UserPersistenceAdapter(userRepository)
    }

    @Bean
    fun userPersistenceAdapter(userRepository: IUserRepository): UserPersistenceAdapter {
        return UserPersistenceAdapter(userRepository)
    }

    @Bean
    fun userService(
        userPersistencePort: UserPersistencePort,
        emailVerificationPort: EmailVerificationPort
    ): UserService {
        return UserService(userPersistencePort, emailVerificationPort)
    }
}
