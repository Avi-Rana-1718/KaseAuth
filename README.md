# KaseAuth

> Part of the [Kase](https://github.com/Kase) project

This service handles identity management, JWT-based authentication, role-based access control, and multi-tenant organization support for the broader Kase ecosystem.

## Features

- User signup and signin with JWT-based authentication
- Role-based access control (RBAC)
- Token refresh and validation endpoints
- BCrypt password hashing
- PostgreSQL database with Flyway migrations
- Multi-tenant support via organization headers

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.0** (Web, Data JPA)
- **PostgreSQL**
- **Flyway** (Database migrations)
- **JJWT** (JSON Web Token library)
- **jBCrypt** (Password hashing)
- **Lombok**
- **Maven**

## Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

## API Endpoints

### Authentication

| Method | Endpoint       | Description          | Headers          |
|--------|----------------|----------------------|------------------|
| POST   | `/auth/signup` | Register a new user  | `org`            |
| POST   | `/auth/signin` | Authenticate user    | `org`            |
| POST   | `/auth/refresh`| Refresh access token | `Authorization`  |
| POST   | `/auth/validate`| Validate token      | `Authorization`  |

### Roles

| Method | Endpoint  | Description           | Headers            |
|--------|-----------|-----------------------|--------------------|
| POST   | `/roles`  | Create a new role     | `X-User-Details`   |
| GET    | `/roles`  | Get all roles for org | `X-User-Details`   |

## Project Structure

```
src/main/java/com/avirana/
├── KaseAuth.java              # Application entry point
├── config/                    # Configuration classes
├── controller/                # REST controllers
├── dto/                       # Data transfer objects
├── entity/                    # JPA entities
├── repository/                # Data repositories
├── service/                   # Business logic services
└── util/                      # Utility classes
```

## License

This project is proprietary.
