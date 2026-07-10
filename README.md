# User Service

A microservice responsible for user management within our platform. It exposes a REST API for creating, retrieving, updating, and deleting user accounts.

## Architecture

The service follows a hexagonal architecture (ports and adapters) pattern:

- **Domain layer** — Contains the core business logic, domain model, and port interfaces (inbound and outbound).
- **Application layer** — Holds DTOs and mappers for translating between the API boundary and the domain.
- **Infrastructure layer** — Implements the adapters: REST controller (inbound), PostgreSQL persistence via R2DBC (outbound), and an HTTP client for email verification (outbound).

## Key Features

- User CRUD operations via REST API
- Email uniqueness validation before account creation
- External email verification through a third-party service
- Reactive stack using Spring WebFlux and Kotlin coroutines

## Tech Stack

- Kotlin 1.9
- Spring Boot 3.2
- Spring WebFlux (reactive)
- R2DBC with PostgreSQL
- Kotlin Coroutines
- Docker Compose for local development

## Running Locally

1. Start the database:
   ```bash
   docker-compose up -d
   ```

2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The API will be available at `http://localhost:8080/api/users`.

## API Endpoints

| Method | Path              | Description         |
|--------|-------------------|---------------------|
| POST   | `/api/users`      | Create a new user   |
| GET    | `/api/users`      | List all users      |
| GET    | `/api/users/{id}` | Get user by ID      |
| PUT    | `/api/users/{id}` | Update user         |
| DELETE | `/api/users/{id}` | Delete user         |

## External Dependencies

- **PostgreSQL** — Primary data store for user records.
- **Email Verification Service** — An external HTTP service called during user creation to validate that the provided email address is deliverable.
