import application.service.NotificationServiceImpl
import domain.model.Notification
import domain.model.NotificationType
import application.service.RateLimiter
import infrastructure.database.DatabaseInitializer
import infrastructure.gateway.EmailNotificationGatewayImpl
import infrastructure.repository.NotificationRepositoryImpl
import java.time.LocalDateTime

fun main() {

    DatabaseInitializer().initialize()

    val repository = NotificationRepositoryImpl()
    val rateLimiter = RateLimiter(repository)

    val emailGateway = EmailNotificationGatewayImpl()

    val notificationService = NotificationServiceImpl(rateLimiter, emailGateway)

    val notifications = listOf(
        Notification(
            type = NotificationType.STATUS,
            recipientId = "user1",
            message = "Status update 1",
            timestamp = LocalDateTime.now()
        ),
        Notification(
            type = NotificationType.STATUS,
            recipientId = "user1",
            message = "Status update 2",
            timestamp = LocalDateTime.now()
        ),
        Notification(
            type = NotificationType.STATUS,
            recipientId = "user1",
            message = "Status update 3",
            timestamp = LocalDateTime.now()
        ),
        Notification(
            type = NotificationType.NEWS,
            recipientId = "user2",
            message = "Daily news 1",
            timestamp = LocalDateTime.now()
        ),
        Notification(
            type = NotificationType.NEWS,
            recipientId = "user2",
            message = "Daily news 2",
            timestamp = LocalDateTime.now()
        )
    )

    for (notification in notifications) {
        notificationService.send(notification)
    }
}
