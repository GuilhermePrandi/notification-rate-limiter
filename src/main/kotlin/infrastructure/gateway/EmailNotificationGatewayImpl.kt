package infrastructure.gateway

import org.slf4j.LoggerFactory
import infrastructure.exception.GatewayException

class EmailNotificationGatewayImpl : NotificationGateway {

    private val logger = LoggerFactory.getLogger(EmailNotificationGatewayImpl::class.java)

    override fun send(recipientId: String, message: String) {
        try {
            logger.info("Sending EMAIL to {}: {}", recipientId, message)
        } catch (e: Exception) {
            throw GatewayException("Failed to send email to $recipientId", e)
        }
    }
}