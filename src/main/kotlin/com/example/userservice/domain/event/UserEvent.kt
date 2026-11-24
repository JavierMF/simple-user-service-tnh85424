package com.example.userservice.domain.event

import com.example.userservice.domain.model.User
import java.time.LocalDateTime

/**
 * Domain events for user lifecycle.
 */
sealed class UserEvent {
    abstract val occurredAt: LocalDateTime
    abstract val userId: Long

    data class UserCreated(
        override val userId: Long,
        val username: String,
        val email: String,
        override val occurredAt: LocalDateTime = LocalDateTime.now()
    ) : UserEvent()

    data class UserDeleted(
        override val userId: Long,
        override val occurredAt: LocalDateTime = LocalDateTime.now()
    ) : UserEvent()

    data class UserUpdated(
        override val userId: Long,
        val previousState: User,
        val newState: User,
        override val occurredAt: LocalDateTime = LocalDateTime.now()
    ) : UserEvent()
}
