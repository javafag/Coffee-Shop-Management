Coffee Shop Management API
A Spring Boot backend application for managing coffee shop operations, including orders, customer data, and waiter performance tracking.

Tech Stack
Java 17

Spring Boot 3.5.11

Gradle

PostgreSQL / H2 Database

Liquibase (Migrations)

SpringDoc OpenAPI (Swagger)

Key Features
REST API V1 for order management.

Waiter performance analytics using JPQL.

Automated database schema migrations via Liquibase.

Global exception handling and request validation.

Getting Started
Prerequisites
JDK 17

PostgreSQL 15 or higher

Installation
Clone the repository.

Update database credentials in src/main/resources/application.properties.

Run the application:
./gradlew bootRun

API Documentation
Once started, the Swagger UI is available at:
http://localhost:8083/swagger-ui/index.html


[29.03.2026 15:55] проект: Hushcha Cousin - это полноценная система управления для современной кофейни, разработанная как демонстрационный проект для резюме бэкенд разработчика. Проект решает реальные бизнес-задачи: управление заказами, бронирование столиков, аналитика продаж и координация работы персонала.

Ключевые возможности:
🏠 Клиентский опыт:

Интерактивное меню с категориями и фильтрацией
Онлайн бронирование 10 столиков (2/3/4 места) с проверкой доступности
Email подтверждения бронирований
История заказов с функцией "Заказать снова"
👨‍💼 Управление бизнесом:

Дашборд с графиками продаж (день/неделя/месяц)
Управление меню и ценами в реальном времени
Блокировка столиков на ремонт
Аналитика популярности блюд и загруженности зала
🫵 Координация персонала:

Ролевая система (ADMIN, MANAGER, WAITER, CUSTOMER)
Назначение столов официантам
Real-time обновление статусов заказов
История работы смен
[29.03.2026 15:55] проект: Технологический стек
Backend:
Java 17 + Spring Boot 3.3.4
Spring Data JPA + PostgreSQL (основная БД)
Spring Security + JWT (аутентификация и авторизация)
Spring WebSocket (real-time обновления)
Liquibase (миграции БД)
Redis (кеширование сессий и данных)
QueryDSL (сложные запросы к БД)
Infrastructure:
Docker + Docker Compose (контейнеризация)
GitHub Actions (CI/CD pipeline)
Prometheus + Grafana (мониторинг)
Logback (централизованное логирование)
Frontend (будет реализован отдельно):
React 18 + TypeScript
TailwindCSS + Headless UI
Chart.js (графики аналитики)
Socket.io-client (WebSocket клиент)
[29.03.2026 15:55] проект: Roadmap развития
Этап 1: Core Backend (2-3 недели)
Базовая структура заказов и официантов
Entity: MenuCategory, Table, Reservation
Business logic для бронирования
Email уведомления
Базовая аналитика
Этап 2: Advanced Features (2 недели)
WebSocket для real-time обновлений
Ролевая система с permissions
Расширенная аналитика с графиками
Кеширование в Redis
Валидация и обработка ошибок
Этап 3: Production Ready (1-2 недели)
Docker контейнеризация
CI/CD pipeline
Monitoring и логирование
Performance оптимизация
Безопасность (rate limiting, CORS)
Этап 4: Интеграции (1 неделя)
Telegram бот для уведомлений
Email сервис интеграция
File storage для изображений
Платежная система (опционально)
Этап 5: Deployment (1 неделя)
Деплой на VPS/cloud
SSL сертификат
Domain настройка
Backup стратегии
Бизнес-ценность проекта
Для работодателя:

Демонстрирует умение создавать full-stack решения
Показывает опыт с современными технологиями
Решает реальные бизнес-задачи
Production-ready архитектура
Технические преимущества:

Масштабируемая микросервисная архитектура
Real-time функционал
Безопасная система авторизации
Полный CI/CD pipeline
Потенциал развития:

Можно расширить до сети кофеен
Добавить мобильное приложение
Интеграция с системами доставки
FRANCHISE модель
[29.03.2026 15:55] проект: Демо-сценарии для работодателя
Клиентский путь: Бронирование → Email подтверждение → История заказов
Менеджерский путь: Дашборд → Управление меню → Аналитика
Официантский путь: Назначение столов → Обновление заказов
Админский путь: Управление пользователями → Мониторинг системы
