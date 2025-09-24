package infrastructure.repository

import domain.model.Notification
import domain.model.NotificationType
import domain.repository.NotificationRepository
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationRepositoryImpl(databaseUrl: String = "jdbc:sqlite:notifications.db") : NotificationRepository {

    private val connection: Connection = DriverManager.getConnection(databaseUrl)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun add(notification: Notification) {
        val sql = "INSERT INTO notifications (id, type, recipient_id, message, timestamp) VALUES (?, ?, ?, ?, ?)"
        connection.prepareStatement(sql).use { pstmt ->
            pstmt.setString(1, notification.id.toString())
            pstmt.setString(2, notification.type.name)
            pstmt.setString(3, notification.recipientId)
            pstmt.setString(4, notification.message)
            pstmt.setString(5, notification.timestamp.format(formatter))
            pstmt.executeUpdate()
        }
    }

    override fun getRecent(
        recipientId: String,
        type: NotificationType,
        windowStart: LocalDateTime
    ): List<Notification> {
        val sql =
            "SELECT id, type, recipient_id, message, timestamp FROM notifications WHERE recipient_id = ? AND type = ? AND timestamp >= ?"
        val result = mutableListOf<Notification>()

        connection.prepareStatement(sql).use { pstmt ->
            pstmt.setString(1, recipientId)
            pstmt.setString(2, type.name)
            pstmt.setString(3, windowStart.format(formatter))

            val rs = pstmt.executeQuery()
            while (rs.next()) {
                result.add(
                    Notification(
                        id = UUID.fromString(rs.getString("id")),
                        type = NotificationType.valueOf(rs.getString("type")),
                        recipientId = rs.getString("recipient_id"),
                        message = rs.getString("message"),
                        timestamp = LocalDateTime.parse(rs.getString("timestamp"), formatter)
                    )
                )
            }
        }
        return result
    }
}
