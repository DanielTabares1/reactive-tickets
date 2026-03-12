# 🛠️ Stack Tecnológico

## Tecnologías Principales

### Backend Framework
- **Spring Boot 3.2+**
    - Spring WebFlux (Reactive Web)
    - Spring Data R2DBC (Reactive Database)
    - Spring Security Reactive
    - Spring Validation

### Lenguaje
- **Java 17+**
    - Records para DTOs
    - Pattern Matching
    - Text Blocks
    - Sealed Classes (opcional)

### Base de Datos
- **PostgreSQL 15+**
    - Driver: R2DBC PostgreSQL
    - Migraciones: Flyway
    - Optimistic Locking con `version`

### Caché
- **Redis 7.0+**
    - Driver: Lettuce (reactive)
    - TTL configurables
    - Pub/Sub para invalidación

### Seguridad
- **Spring Security Reactive**
- **JWT (JSON Web Tokens)**
    - Librería: `io.jsonwebtoken:jjwt`
    - Algoritmo: HS256
- **BCrypt** para hashing de passwords

### Build Tool
- **Gradle 8.5+**
    - Kotlin DSL (opcional)
    - Dependency Management

### Testing
- **JUnit 5** (Jupiter)
- **Reactor Test** (StepVerifier)
- **Testcontainers** (PostgreSQL, Redis)
- **Mockito** para mocks
- **AssertJ** para assertions

### Logging
- **SLF4J + Logback**
- **Structured Logging** (JSON)

### Documentación API
- **SpringDoc OpenAPI** (WebFlux)
- Swagger UI integrado

### Monitoreo (Opcional)
- **Spring Boot Actuator**
- **Micrometer** para métricas
- **Prometheus** (exportación de métricas)

---

## Dependencias de Gradle

### build.gradle

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.3'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.tuusuario'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring WebFlux
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    
    // R2DBC
    implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'
    implementation 'org.postgresql:r2dbc-postgresql:1.0.4.RELEASE'
    
    // Redis Reactive
    implementation 'org.springframework.boot:spring-boot-starter-data-redis-reactive'
    implementation 'io.lettuce:lettuce-core'
    
    // Security
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
    
    // Validation
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    
    // Flyway (migraciones)
    implementation 'org.flywaydb:flyway-core'
    runtimeOnly 'org.postgresql:postgresql' // JDBC para Flyway
    
    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    // OpenAPI Documentation
    implementation 'org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0'
    
    // Actuator (Monitoring)
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    
    // Resilience4j (Circuit Breaker, Rate Limiter)
    implementation 'io.github.resilience4j:resilience4j-spring-boot3:2.2.0'
    implementation 'io.github.resilience4j:resilience4j-reactor:2.2.0'
    
    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'io.projectreactor:reactor-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testImplementation 'org.testcontainers:testcontainers:1.19.6'
    testImplementation 'org.testcontainers:postgresql:1.19.6'
    testImplementation 'org.testcontainers:r2dbc:1.19.6'
    testImplementation 'org.testcontainers:junit-jupiter:1.19.6'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

---

## Configuración de Aplicación

### application.yaml

```yaml
spring:
  application:
    name: event-booking-system
  
  # R2DBC Configuration
  r2dbc:
    url: r2dbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:eventbooking}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    pool:
      initial-size: 10
      max-size: 50
      max-idle-time: 30m
      validation-query: SELECT 1
  
  # Flyway Configuration (usa JDBC)
  flyway:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:eventbooking}
    user: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    baseline-on-migrate: true
    locations: classpath:db/migration
  
  # Redis Configuration
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:}
      timeout: 2000ms
      lettuce:
        pool:
          max-active: 20
          max-idle: 10
          min-idle: 5
  
  # Security
  security:
    jwt:
      secret: ${JWT_SECRET:your-secret-key-change-in-production}
      expiration: 86400000 # 24 horas en ms
      refresh-expiration: 604800000 # 7 días en ms

# Server Configuration
server:
  port: ${PORT:8080}
  netty:
    connection-timeout: 10s
    idle-timeout: 60s

# Logging
logging:
  level:
    root: INFO
    com.tuusuario.eventbooking: DEBUG
    org.springframework.r2dbc: DEBUG
    io.r2dbc.postgresql.QUERY: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

# Resilience4j
resilience4j:
  circuitbreaker:
    instances:
      pagoService:
        sliding-window-size: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10000
        permitted-number-of-calls-in-half-open-state: 3
  
  ratelimiter:
    instances:
      reservaApi:
        limit-for-period: 10
        limit-refresh-period: 60s
        timeout-duration: 0s

# Application Specific
app:
  reserva:
    expiration-minutes: 10
    max-tickets-per-reserva: 10
  cache:
    disponibilidad-ttl: 10 # segundos
    evento-ttl: 300 # segundos
  scheduler:
    expiration-check-rate: 60000 # 1 minuto en ms
```

### application-dev.yaml

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/eventbooking_dev
  flyway:
    url: jdbc:postgresql://localhost:5432/eventbooking_dev

logging:
  level:
    com.tuusuario.eventbooking: DEBUG
    org.springframework.r2dbc: DEBUG
```

### application-prod.yaml

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    pool:
      max-size: 100
  
logging:
  level:
    root: WARN
    com.tuusuario.eventbooking: INFO

server:
  port: 8080
```

---

## Docker Compose para Desarrollo

### docker-compose.yml

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: eventbooking-postgres
    environment:
      POSTGRES_DB: eventbooking
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7-alpine
    container_name: eventbooking-redis
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  postgres_data:
  redis_data:
```

### Comandos Docker

```bash
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Limpiar todo (incluyendo volúmenes)
docker-compose down -v
```

---

## Herramientas de Desarrollo

### IDE Recomendado
- **IntelliJ IDEA** (Ultimate o Community)
- **VS Code** con extensiones Java

### Extensiones Útiles
- Lombok Plugin
- Spring Boot Tools
- Database Navigator
- Rainbow Brackets

### Cliente de Base de Datos
- **DBeaver** (gratuito)
- **pgAdmin** (PostgreSQL específico)
- **DataGrip** (JetBrains)

### Cliente de Redis
- **RedisInsight** (oficial)
- **Redis Commander**

### Cliente HTTP
- **Postman**
- **Insomnia**
- **HTTPie** (CLI)

### Monitoreo Local
- **Prometheus** + **Grafana** (opcional)
- **Spring Boot Admin** (opcional)

---

## Comandos Útiles

### Gradle

```bash
# Compilar proyecto
./gradlew build

# Ejecutar aplicación
./gradlew bootRun

# Ejecutar tests
./gradlew test

# Limpiar build
./gradlew clean

# Ver dependencias
./gradlew dependencies
```

### PostgreSQL

```bash
# Conectar a la base de datos
psql -h localhost -U postgres -d eventbooking

# Listar tablas
\dt

# Describir tabla
\d evento

# Ver datos
SELECT * FROM evento;
```

### Redis

```bash
# Conectar a Redis
redis-cli

# Ver todas las keys
KEYS *

# Ver valor de una key
GET disponibilidad:evento:1

# Limpiar todo
FLUSHALL
```

---

## Versiones Recomendadas

| Tecnología | Versión Mínima | Versión Recomendada |
|------------|----------------|---------------------|
| Java | 17             | 21                  |
| Spring Boot | 4.0.0          | 4.0.3               |
| PostgreSQL | 14             | 15                  |
| Redis | 6.2            | 7.0                 |
| Gradle | 8.0            | 8.5                 |
| Docker | 20.10          | 24.0                |

---

## Próximos Pasos

1. Instalar Java 17+
2. Instalar Docker y Docker Compose
3. Clonar/crear proyecto con estructura hexagonal
4. Configurar `build.gradle` con dependencias
5. Levantar PostgreSQL y Redis con Docker Compose
6. Comenzar con HU-01
