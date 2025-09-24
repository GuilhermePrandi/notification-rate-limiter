package domain.model

import java.time.Duration

data class RateLimitRule(
    val type: NotificationType,
    val maxMessages: Int,
    val window: Duration
)