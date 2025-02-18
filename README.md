# Сервис Диалогов для Социальной Сети

## Обзор

**Social Network Dialog** — это микросервис, разработанный для обработки сообщений и управления диалогами в рамках социальной сети. Он предоставляет основные функции для создания, отправки и управления диалогами между пользователями с использованием REST API и WebSocket для обмена сообщениями в реальном времени.

![image](https://github.com/user-attachments/assets/9543249e-55b1-4554-90bc-b031fcc56e37)


## Основные функции

- **Управление диалогами**: Создание, обновление и удаление диалогов.
- **Обмен сообщениями в реальном времени**: Использование WebSocket STOMP в качестве брокера RabbitMQ для мгновенной передачи сообщений между пользователями.
- **Безопасная аутентификация**: Аутентификация и авторизация с использованием JWT токенов.
- **Микросервисная архитектура**: Использование Eureka для регистрации и поиска сервисов, а также Feign для взаимодействия между микросервисами.
- **Миграции базы данных**: Управляются с помощью Liquibase для обеспечения плавного обновления базы данных.
- **Событийная архитектура**: Использование Kafka для обработки фона и событий в системе.

## Используемые технологии

- **Java 17**: Основной язык программирования.
- **Spring Boot 3.3.2**: Фреймворк для разработки микросервисов.
  - **Spring Boot Starter Web**: Для создания REST API.
  - **Spring Boot Starter WebSocket**: Для обмена сообщениями в реальном времени.
  - **Spring Boot Starter Security**: Для аутентификации и авторизации.
  - **Spring Boot Starter Data JPA**: Для работы с базой данных PostgreSQL.
  - **Spring Boot Starter AMQP**: Для обмена сообщениями через RabbitMQ.
- **Spring Cloud Netflix Eureka Client**: Для регистрации и поиска микросервисов.
- **Spring Cloud OpenFeign**: Для упрощения HTTP запросов к другим микросервисам.
- **JWT (JSON Web Token)**: Используется для безопасной аутентификации пользователей.
- **PostgreSQL**: Реляционная база данных для хранения диалогов и сообщений.
- **Liquibase**: Для управления миграциями и версиями базы данных.
- **Apache Kafka**: Для событийного взаимодействия между микросервисами.
- **RabbitMQ**: Брокер сообщений для асинхронного обмена.
- **Lombok**: Для уменьшения шаблонного кода (геттеры, сеттеры и т.д.).
- **Maven**: Инструмент для управления проектом и его сборки.
- **Testcontainers**: Для интеграционного тестирования с использованием контейнеров.
  - **JUnit 5**: Фреймворк для тестирования.
  - **Jacoco**: Инструмент для покрытия кода тестами.
  - **SonarQube**: Для статического анализа кода и контроля качества.

## Описание

Данный проект является частью микросервисного приложения социльной сети для разработчиков. Обособленно его запустить не получится, демонстрирует реализацю протокола STOMP с использованием RabbitMQ в качестве брокера сообщений.

![image](https://github.com/user-attachments/assets/ce435d2e-a618-4aec-a065-52345a544721)




