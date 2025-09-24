package application.service

import application.exception.NotificationException
import domain.model.Notification
import domain.model.NotificationType
import infrastructure.gateway.NotificationGateway
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals

class NotificationServiceImplTest {

    private lateinit var rateLimiter: RateLimiter
    private lateinit var gateway: NotificationGateway
    private lateinit var service: NotificationServiceImpl

    @BeforeEach
    fun setup() {
        rateLimiter = mockk()
        gateway = mockk(relaxed = true)
        service = NotificationServiceImpl(rateLimiter, gateway)
    }

    @ParameterizedTest
    @EnumSource(NotificationType::class)
    fun `should send notification when allowed`(type: NotificationType) {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = type,
            recipientId = "user-${type.name}",
            message = "${type.name} message"
        )
        every { rateLimiter.allow(notification) } returns true

        service.send(notification)

        verify { gateway.send(notification.recipientId, notification.message) }
    }

    @ParameterizedTest
    @EnumSource(NotificationType::class)
    fun `should not send notification when blocked`(type: NotificationType) {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = type,
            recipientId = "user-${type.name}",
            message = "Blocked message"
        )
        every { rateLimiter.allow(notification) } returns false

        service.send(notification)

        verify(exactly = 0) { gateway.send(any(), any()) }
    }

    @Test
    fun `should throw NotificationException when gateway fails`() {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = NotificationType.MARKETING,
            recipientId = "user-marketing",
            message = "Critical error"
        )
        every { rateLimiter.allow(notification) } returns true
        every { gateway.send(notification.recipientId, notification.message) } throws RuntimeException("gateway down")

        val exception = assertThrows<NotificationException> {
            service.send(notification)
        }

        assertEquals("Unexpected error for notification to ${notification.recipientId}", exception.message)
    }

    @Test
    fun `should correctly call gateway multiple times when allowed`() {
        val notifications = listOf(
            Notification(UUID.randomUUID(), NotificationType.STATUS, "user1", "msg1"),
            Notification(UUID.randomUUID(), NotificationType.STATUS, "user1", "msg2"),
            Notification(UUID.randomUUID(), NotificationType.NEWS, "user2", "msg1")
        )

        notifications.forEach { every { rateLimiter.allow(it) } returns true }

        notifications.forEach { service.send(it) }

        notifications.forEach {
            verify { gateway.send(it.recipientId, it.message) }
        }
    }
}
