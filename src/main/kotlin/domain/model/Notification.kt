package domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Notification(
    val id: UUID = UUID.randomUUID(),
    val type: NotificationType,
    val recipientId: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)