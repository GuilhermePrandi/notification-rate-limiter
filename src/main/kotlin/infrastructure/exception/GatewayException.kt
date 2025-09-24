package infrastructure.exception

class GatewayException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
