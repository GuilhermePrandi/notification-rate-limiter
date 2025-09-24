import application.service.NotificationServiceImpl
import application.service.RateLimiter
import infrastructure.database.DatabaseInitializer
import infrastructure.gateway.EmailNotificationGatewayImpl
import infrastructure.repository.NotificationRepositoryImpl
import utils.SampleNotifications

fun main() {

    val databaseUrl = "jdbc:sqlite:notifications.db"

    DatabaseInitializer(databaseUrl).initialize()
    val repository = NotificationRepositoryImpl(databaseUrl)

    val rateLimiter = RateLimiter(repository)

    val emailGateway = EmailNotificationGatewayImpl()

    val notificationService = NotificationServiceImpl(rateLimiter, emailGateway)

    for (notification in SampleNotifications.list) {
        notificationService.send(notification)
    }
}
