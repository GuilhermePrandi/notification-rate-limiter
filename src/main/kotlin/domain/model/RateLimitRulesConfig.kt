package domain.model

import java.time.Duration

object RateLimitRulesConfig {
    val rules: Map<NotificationType, RateLimitRule> = mapOf(
        NotificationType.STATUS to RateLimitRule(
            type = NotificationType.STATUS,
            maxMessages = 2,
            window = Duration.ofMinutes(1)
        ),
        NotificationType.NEWS to RateLimitRule(
            type = NotificationType.NEWS,
            maxMessages = 1,
            window = Duration.ofDays(1)
        ),
        NotificationType.MARKETING to RateLimitRule(
            type = NotificationType.MARKETING,
            maxMessages = 3,
            window = Duration.ofHours(1)
        )
    )
}
