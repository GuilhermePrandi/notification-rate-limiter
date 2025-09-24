package application.service

import application.exception.NotificationException
import domain.model.Notification
import infrastructure.gateway.NotificationGateway
import org.slf4j.LoggerFactory

class NotificationServiceImpl(
    private val rateLimiter: RateLimiter,
    private val gateway: NotificationGateway
) : NotificationService {

    private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)

    override fun send(notification: Notification) {
        try {
            if (rateLimiter.allow(notification)) {
                gateway.send(notification.recipientId, notification.message)
                logger.info("Notification sent: type=${notification.type}, recipient=${notification.recipientId}")
            } else {
                logger.warn("Notification blocked by rate limiter: type=${notification.type}, recipient=${notification.recipientId}")
            }
        } catch (e: Exception) {
            logger.error("Unexpected error while sending notification to ${notification.recipientId}", e)
            throw NotificationException("Unexpected error for notification to ${notification.recipientId}", e)
        }
    }
}
