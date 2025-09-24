package utils

import domain.model.Notification
import domain.model.NotificationType
import java.time.LocalDateTime

object SampleNotifications {

    val list: List<Notification> = listOf(
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
        ),
        Notification(
            type = NotificationType.MARKETING,
            recipientId = "user3",
            message = "Marketing 1",
            timestamp = LocalDateTime.now()
        )
    )
}
