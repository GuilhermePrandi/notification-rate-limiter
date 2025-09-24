package application.service

import domain.model.Notification

interface NotificationService {
    fun send(notification: Notification)
}