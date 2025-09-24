package infrastructure.gateway

interface NotificationGateway {
    fun send(recipientId: String, message: String)
}