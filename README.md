# Notification Rate Limiter

A Kotlin-based backend project that implements a **rate-limited notification service**.  
This service prevents users from receiving too many notifications, enforcing limits by notification type.

---

## 🏗 Architecture

The project is divided into four main layers:

### 1️⃣ Domain
- **Contains:** business entities and rules.
- **Examples:** `Notification`, `NotificationType`, `RateLimitRule`, `RateLimitRulesConfig`.
- **Responsibility:** model notifications and rate limit rules. No dependencies on infrastructure.

### 2️⃣ Application
- **Contains:** application logic that orchestrates the flow.
- **Examples:** `NotificationServiceImpl`, `RateLimiter`.
- **Responsibility:**  
  - `NotificationServiceImpl` decides whether to send or block a notification, logs activity, and handles exceptions.  
  - `RateLimiter` enforces rate limits, queries, and updates the repository.  
- Depends on **Domain** for models/rules and **Infrastructure** for persistence/gateway.

### 3️⃣ Infrastructure
- **Contains:** concrete implementations for persistence and external systems.
- **Examples:** `NotificationRepositoryImpl` (SQLite), `NotificationGateway` (console/mock).  
- **Responsibility:** persist notifications and deliver them to recipients. Abstracts infrastructure details from the rest of the app.

### 4️⃣ Utils

- **Contains:** helper classes and objects that support the project, mainly for test data and examples.
- **Examples:** `SampleNotifications` provides a list of example Notification objects for use in the main function or in tests, centralizing and organizing test data.

---

## 📝 Features

- Rate limiting per notification type:
  - Example rules (hardcoded in `RateLimitRulesConfig`):
    - `STATUS`: max 2 per minute per recipient
    - `NEWS`: max 1 per day per recipient
    - `MARKETING`: max 3 per hour per recipient
- Logging of sent and blocked notifications
- Exception handling for failed deliveries
- Testable architecture with unit and integration tests
- **Future improvement:** dynamic configuration of rate limits via JSON, YAML, or `application.properties`.

---

## 🧰 Requirements

Java 17 (JDK 17)

Gradle 8.x

---

## 🚀 Running the Application

This will:

- Initialize the SQLite database (creating the notifications table if it doesn't exist).

- Populate it with sample notifications.

- Send the notifications through the NotificationService.

```bash
./gradlew run
```

---

## 🧪 Running Tests

This project uses **JUnit 5** and **MockK**.

```bash
./gradlew test
```



