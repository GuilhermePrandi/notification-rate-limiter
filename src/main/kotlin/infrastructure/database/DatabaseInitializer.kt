package infrastructure.database

import java.sql.DriverManager

class DatabaseInitializer(private val databaseUrl: String = "jdbc:sqlite:notifications.db") {

    fun initialize() {
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.createStatement().use { stmt ->
                stmt.execute(
                    """
                    CREATE TABLE IF NOT EXISTS notifications (
                        id TEXT PRIMARY KEY,
                        type TEXT NOT NULL,
                        recipient_id TEXT NOT NULL,
                        message TEXT NOT NULL,
                        timestamp TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
