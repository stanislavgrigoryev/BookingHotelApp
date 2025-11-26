Hotel Booking Application
Spring Boot приложение для управления отелями, бронированиями и пользователями с системой мониторинга и статистики.

Функциональность:
Основные модули
Управление отелями - CRUD операции, рейтинги, фильтрация
Управление комнатами - бронирование, проверка доступности
Пользователи и аутентификация - регистрация, роли (USER/ADMIN)
Бронирования - система бронирования с проверкой конфликтов
Статистика - сбор и анализ данных о пользователях и бронированиях

Безопасность:
Spring Security с Basic Auth
Ролевая модель доступа (USER, ADMIN)
Защищенные endpoints

Мониторинг:
Метрики приложения через Spring Actuator
Визуализация в Grafana
Хранение метрик в VictoriaMetrics
Сбор статистики через Kafka в MongoDB

Технологии:
Backend
Java 17 + Spring Boot 3.5, Spring Data JPA (PostgreSQL), Spring Security, Spring Kafka, Liquibase (миграции БД)

Базы данных:
PostgreSQL - основные данные (отели, пользователи, бронирования)
MongoDB - статистика и события

Инфраструктура:
Docker + Docker Compose
Kafka + Zookeeper - обработка событий

VictoriaMetrics + Grafana - мониторинг

Prometheus - сбор метрик
