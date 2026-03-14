# Практические задания по Spring Data JPA и Spring Security

## Spring Data JPA Tasks

### Задание 1: Расширение модели данных
**Цель:** Практика сущностей и связей

1. **Добавь сущность `MenuItem` (Позиция меню)**
   - Поля: id, name, description, price, category
   - Используй @Entity, @Table, @Id, @GeneratedValue
   - Добавь валидацию (@Column(nullable = false), @Size, @DecimalMin)

2. **Создай связь ManyToMany между Order и MenuItem**
   - У заказа может быть несколько позиций меню
   - Одна позиция может быть в нескольких заказах
   - Используй @JoinTable с proper naming

3. **Добавь сущность `OrderItem` для детализации**
   - Связь @ManyToOne с Order и MenuItem
   - Поля: quantity, unitPrice, totalPrice

### Задание 2: Repository и кастомные запросы
**Цель:** Практика JpaRepository и JPQL

1. **Расширь репозитории:**
   ```java
   // CustomerRepository
   List<Customer> findByNameContainingIgnoreCase(String name);
   List<Customer> findByNameAndEmail(String name, String email);
   
   // OrderRepository  
   List<CsfOrder> findByCustomerIdAndStatus(Long customerId, String status);
   List<CsfOrder> findByWaiterIdAndOrderDateBetween(Long waiterId, LocalDateTime start, LocalDateTime end);
   ```

2. **Добавь JPQL запросы:**
   - Найти все заказы клиента с суммой больше X
   - Подсчитать количество заказов каждого официанта
   - Найти самые популярные напитки

3. **Создай Native Query:**
   - Сложный отчет по продажам за период
   - Статистика по официантам

### Задание 3: QueryDSL / Criteria API
**Цель:** Динамические запросы

1. **Добавь QueryDSL зависимости**
2. **Создай динамический поиск заказов:**
   - Фильтр по дате, статусу, клиенту, официанту
   - Сортировка по любому полю
   - Пагинация

3. **Реализуй Criteria API для сложной фильтрации:**
   - Поиск по нескольким полям клиента
   - Диапазоны цен и дат

### Задание 4: Транзакции и производительность
**Цель:** @Transactional и оптимизация

1. **Добавь @Transactional в сервисы:**
   - REQUIRED для основных операций
   - REQUIRES_NEW для логирования
   - readOnly для операций чтения

2. **Оптимизируй N+1 проблему:**
   - Используй @EntityGraph
   - JOIN FETCH в JPQL
   - Batch fetching

3. **Настройка HikariCP:**
   - Конфигурация пула соединений
   - Мониторинг производительности

### Задание 5: Миграции Liquibase
**Цель:** Управление схемой БД

1. **Добавь Liquibase:**
   - Создай changelog для начальных данных
   - Добавь индексы для производительности
   - Создай seed data

## Spring Security Tasks

### Задание 6: Расширение аутентификации
**Цель:** JWT и роли

1. **Создай сущность User с ролями:**
   - Поля: username, password, email, role
   - Enum: ROLE_ADMIN, ROLE_WAITER, ROLE_CUSTOMER

2. **Улучши JWT:**
   - Добавь refresh tokens
   - Валидация токенов
   - Обработка истечения срока

3. **Реализуй UserDetails:**
   - Кастомный UserDetailsService
   - Загрузка пользователя из БД

### Задание 7: Авторизация на уровне методов
**Цель:** Security annotations

1. **Добавь @PreAuthorize:**
   ```java
   @PreAuthorize("hasRole('ADMIN')")
   public void deleteUser(Long id);
   
   @PreAuthorize("hasRole('WAITER') or #order.waiter.id == authentication.principal.id")
   public void updateOrderStatus(Long orderId, String status);
   
   @PreAuthorize("hasRole('CUSTOMER') and #customerId == authentication.principal.customerId")
   public List<CsfOrder> getCustomerOrders(Long customerId);
   ```

2. **Создай кастомные security expressions:**
   - Проверка владельца заказа
   - Проверка прав на конкретные данные

### Задание 8: OAuth2 интеграция
**Цель:** Понимание OAuth2 flow

1. **Добавь OAuth2 login:**
   - Google/Facebook login
   - Mapping OAuth2 пользователей к сущностям

2. **Resource Server:**
   - Защита API endpoints
   - Scope-based access

### Задание 9: Security testing
**Цель:** Тестирование безопасности

1. **Напиши security тесты:**
   - Тесты авторизации endpoints
   - Тесты JWT генерации/валидации
   - Интеграционные тесты с @WithMockUser

## Комплексное задание: Coffee Shop Management

### Финальный проект: Полнофункциональное управление кофейней

**Требования:**

1. **Пользователи и роли:**
   - Admin: полный доступ
   - Waiter: управление заказами
   - Customer: просмотр своих заказов

2. **Функционал:**
   - Регистрация/аутентификация с JWT
   - CRUD для всех сущностей
   - Поиск и фильтрация заказов
   - Статистика и отчеты
   - Валидация данных

3. **Безопасность:**
   - Защита всех endpoints
   - Права доступа по ролям
   - Rate limiting
   - CORS конфигурация

4. **Производительность:**
   - Оптимизированные запросы
   - Кеширование
   - Connection pooling

5. **Тестирование:**
   - Unit тесты для сервисов
   - Integration тесты для контроллеров
   - Security тесты

## Порядок выполнения

1. Начни с Spring Data JPA (задания 1-5)
2. Переходи к Spring Security (задания 6-9)
3. Заверши комплексным проектом

**Совет:** Делай коммиты после каждого завершенного задания для отслеживания прогресса!
