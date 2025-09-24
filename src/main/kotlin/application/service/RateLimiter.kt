package application.service

import domain.repository.NotificationRepository
import domain.model.Notification
import domain.model.NotificationType
import domain.model.RateLimitRule
import domain.model.RateLimitRulesConfig

class RateLimiter(
    private val repository: NotificationRepository,
    private val rules: Map<NotificationType, RateLimitRule> = RateLimitRulesConfig.rules
) {

    fun allow(notification: Notification): Boolean {
        val rule = rules[notification.type] ?: return true
        val windowStart = notification.timestamp.minus(rule.window)

        val recent = repository.getRecent(notification.recipientId, notification.type, windowStart)
        val allowed = recent.size < rule.maxMessages

        if (allowed) repository.add(notification)

        return allowed
    }
}