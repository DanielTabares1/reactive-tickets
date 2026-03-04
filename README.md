# 🎪 Sistema de Reserva de Tickets para Eventos

## Descripción del Proyecto

Plataforma reactiva para venta de tickets de eventos (conciertos, conferencias, deportes) construida con Spring WebFlux y arquitectura hexagonal. El sistema maneja alta concurrencia durante la venta, múltiples zonas con precios diferentes, y actualizaciones en tiempo real de disponibilidad.

## 🎯 Objetivos de Aprendizaje

- Dominar programación reactiva con WebFlux
- Implementar arquitectura hexagonal (Clean Architecture)
- Manejar concurrencia y race conditions con R2DBC
- Construir APIs en tiempo real con WebSocket y SSE
- Aplicar patrones de resiliencia (circuit breaker, retry, rate limiting)
- Gestionar transacciones reactivas complejas

## 📚 Documentación

- [Arquitectura del Sistema](./docs/arquitectura.md) - Diagramas y estructura hexagonal
- [Modelo de Datos](./docs/modelo-datos.md) - Esquema de base de datos y relaciones
- [Historias de Usuario](./docs/historias-usuario.md) - Roadmap completo del proyecto
- [Stack Tecnológico](./docs/stack-tecnologico.md) - Tecnologías y dependencias

## 🚀 Características Principales

### Funcionalidades Core
- ✅ Gestión de eventos con múltiples zonas
- ✅ Reservas temporales con expiración automática
- ✅ Control de concurrencia para evitar overselling
- ✅ Confirmación de reservas y generación de tickets
- ✅ Autenticación y autorización con JWT

### Tiempo Real
- ✅ WebSocket para disponibilidad en tiempo real
- ✅ Server-Sent Events para notificaciones
- ✅ Dashboard con métricas live

### Resiliencia
- ✅ Optimistic locking para concurrencia
- ✅ Scheduled tasks reactivos
- ✅ Caché con Redis
- ✅ Circuit breaker y retry patterns

## 🛠️ Stack Tecnológico

- **Framework:** Spring Boot 4.0+ (WebFlux)
- **Lenguaje:** Java 21+
- **Base de datos:** PostgreSQL 15+ con R2DBC
- **Caché:** Redis (Lettuce reactive)
- **Seguridad:** Spring Security Reactive + JWT
- **Build:** Gradle

## 📦 Estructura del Proyecto

```
event-booking-system/
├── domain/              # Lógica de negocio pura
├── application/         # Casos de uso y puertos
└── infrastructure/      # Adaptadores (REST, DB, etc.)
```

Ver [arquitectura.md](./arquitectura.md) para detalles completos.

## 🎓 Roadmap de Desarrollo

El proyecto se desarrolla en 12 historias de usuario incrementales:

1. **HU-01:** Gestión básica de eventos
2. **HU-02:** Zonas y precios
3. **HU-03:** Consulta de disponibilidad
4. **HU-04:** Reserva de tickets
5. **HU-05:** Liberación automática de reservas
6. **HU-06:** Confirmación de reserva
7. **HU-07:** Autenticación y usuarios
8. **HU-08:** Mis reservas
9. **HU-09:** WebSocket - Disponibilidad en tiempo real
10. **HU-10:** SSE - Notificaciones
11. **HU-11:** Dashboard de métricas
12. **HU-12:** Optimizaciones y resiliencia

Ver [historias-usuario.md](./historias-usuario.md) para detalles completos.

## 🏁 Comenzar

1. Revisar la documentación en orden:
    - Arquitectura
    - Modelo de datos
    - Historias de usuario
    - Stack tecnológico

2. Configurar el entorno de desarrollo

3. Comenzar con HU-01 (Gestión básica de eventos)

## 📝 Notas

Este proyecto está diseñado para aprendizaje incremental. Cada historia de usuario es funcional y entregable por sí misma, permitiendo validar el progreso en cada etapa.
