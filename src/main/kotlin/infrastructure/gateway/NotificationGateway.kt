package infrastructure.gateway

import domain.model.NotificationType

interface NotificationGateway {
    fun send(recipientId: String, message: String, type: NotificationType)
}