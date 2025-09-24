package application.service

import domain.model.Notification
import domain.model.NotificationType
import domain.model.RateLimitRule
import domain.repository.NotificationRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RateLimiterTest {

    private lateinit var repository: NotificationRepository
    private lateinit var rateLimiter: RateLimiter

    @BeforeEach
    fun setup() {
        val statusRule = RateLimitRule(
            type = NotificationType.STATUS,
            maxMessages = 2,
            window = Duration.ofMinutes(5)
        )
        repository = mockk()
        rateLimiter = RateLimiter(
            repository,
            rules = mapOf(NotificationType.STATUS to statusRule)
        )
    }

    @Test
    fun `should allow when no rule exists for type`() {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = NotificationType.NEWS,
            recipientId = "user-1",
            message = "News message"
        )

        val result = rateLimiter.allow(notification)

        assertTrue(result)
        verify(exactly = 0) { repository.add(any()) }
    }

    @Test
    fun `should allow and save when under the limit`() {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = NotificationType.STATUS,
            recipientId = "user-2",
            message = "System ok",
            timestamp = LocalDateTime.now()
        )
        every {
            repository.getRecent(notification.recipientId, notification.type, any())
        } returns emptyList()

        every { repository.add(notification) } just Runs

        val result = rateLimiter.allow(notification)

        assertTrue(result)
        verify { repository.add(notification) }
    }

    @Test
    fun `should block when over the limit`() {
        val notification = Notification(
            id = UUID.randomUUID(),
            type = NotificationType.STATUS,
            recipientId = "user-3",
            message = "Too many requests",
            timestamp = LocalDateTime.now()
        )
        val oldNotifications = listOf(
            notification.copy(id = UUID.randomUUID()),
            notification.copy(id = UUID.randomUUID())
        )
        every {
            repository.getRecent(notification.recipientId, notification.type, any())
        } returns oldNotifications

        val result = rateLimiter.allow(notification)

        assertFalse(result)
        verify(exactly = 0) { repository.add(any()) }
    }
}
