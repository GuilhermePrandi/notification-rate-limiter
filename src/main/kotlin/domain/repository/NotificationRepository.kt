package domain.repository

import domain.model.Notification
import domain.model.NotificationType
import java.time.LocalDateTime

interface NotificationRepository {
    fun add(notification: Notification)

    fun getRecent(recipientId: String, type: NotificationType, windowStart: LocalDateTime): List<Notification>
}