package infrastructure.gateway

import domain.model.NotificationType
import org.slf4j.LoggerFactory
import infrastructure.exception.GatewayException

class EmailNotificationGatewayImpl : NotificationGateway {

    private val logger = LoggerFactory.getLogger(EmailNotificationGatewayImpl::class.java)

    override fun send(recipientId: String, message: String, type: NotificationType) {
        try {
            logger.info("Sending e-mail: type=$type, recipient=$recipientId, message=$message")
        } catch (e: Exception) {
            throw GatewayException("Failed to send email to $recipientId", e)
        }
    }
}