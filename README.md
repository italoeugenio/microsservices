# About this project

This project was created for me to study microservices, and I put a lot of things that I wanted to learn, such as:

- Creating mappers without libraries
- Enums with custom `fromApi` / `toApi` methods
- RabbitMQ as a message broker between services
- JWT authentication with access token + refresh token rotation and reuse detection
- Clean Architecture in the email service to avoid coupling
- Docker network isolation — each container only sees what it needs
- Nginx as API gateway and load balancer
- Monitoring with Prometheus + Grafana

---

# How to Run the Project

## Requirements

- Docker
- Docker Compose

---

## Environment Variables Setup

Before starting, create the following `.env` files:

### Root `.env`

```env
# RABBITMQ
RABBITMQ_USER=dev
RABBITMQ_PASSWORD=dev

# Microservice ports
USER_PORT=8081
EMAIL_PORT=8080
STOCK_PORT=8082

# User Database
DB_USER=dev
DB_PASSWORD=dev
DB_NAME_USER=ms-user

# Stock Database
DB_NAME_STOCK=ms-stock-control

# Grafana
GRAFANA_USER=dev
GRAFANA_PASSWORD=dev
```

### `email-api/.env`

```env
RESEND_API_KEY=your_resend_api_key
RESEND_FROM_EMAIL=your_email@example.com
RABBITMQ_HOST=rabbitmq
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=dev
RABBITMQ_PASSWORD=dev
RABBITMQ_QUEUE_NAME=default.email
```

### `stock-control-api/.env`

```env
SERVER_PORT=8082
DB_PORT=5432
DB_NAME=ms-stock-control
DB_USER=dev
DB_HOST=stock-database
DB_PASSWORD=dev
JWT_TOKEN=my-secret
```

### `user-api/.env`

```env
JWT_TOKEN=my-secret
DB_HOST=user-database
DB_PORT=5432
DB_NAME=ms-user
DB_USER=dev
DB_PASSWORD=dev
SERVER_PORT=8081
RABBITMQ_PORT=5672
RABBITMQ_HOST=rabbitmq
RABBITMQ_USERNAME=dev
RABBITMQ_PASSWORD=dev
RABBITMQ_QUEUE_NAME=default.email
```

---

## Run with Docker

```bash
# Start all containers
docker compose up -d --build

# View logs from a specific container
docker logs <container-name> -f

# Stop all containers
docker compose down
```

## Access the Services

| Service             | URL                    |
| ------------------- | ---------------------- |
| API (via Nginx)     | http://localhost:8000  |
| RabbitMQ Management | http://localhost:15672 |
| Prometheus          | http://localhost:9090  |
| Grafana             | http://localhost:3000  |
| pgAdmin             | http://localhost:4332  |

## Default Admin Credentials

The `user-api` Flyway migration seeds a default admin user:

```json
{
  "email": "admin@application.com",
  "password": "admin2026wW@"
}
```

---

# Project Overview

This monorepo contains three Spring Boot services orchestrated with Docker Compose:

### 🧑 user-api (port 8081)

Handles authentication and authorization. Features include:

- Register, login, email verification
- Change password, recover password, reset password
- JWT access token (15 min expiry) + refresh token (7 days) with rotation
- Reuse detection: if a revoked refresh token is used, all sessions are immediately terminated
- Sends emails via RabbitMQ → email-service

### ⌚ stock-control-api (port 8082)

Full CRUD for watch and brand inventory. Features include:

- Dynamic multi-parameter filtering using JPA `Specification`
- Dynamic sorting (price, diameter, water resistance, newest)
- Role-based access: `CLIENT` for reads, `ADMIN` for writes
- JWT validation shared via the same secret as user-api
- Flyway migrations + seed data

### 📧 email-api (port 8080)

Not a REST API — it's a pure RabbitMQ consumer following Clean Architecture:

- `EmailSenderUseCase` (core) → `EmailSenderGateway` (adapter) → `ResendEmailSenderService` (infra)
- Sends transactional emails using [Resend](https://resend.com)

### 🌐 Nginx (port 8000)

Acts as the API gateway and load balancer. Routes:

- `/auth/**` → user-service
- `/api/v1/brand/**` → stock-service
- `/api/v1/watch/**` → stock-service
- `/server` → load balanced between user-service and stock-service

### 📊 Monitoring

- **Prometheus** (port 9090) — scrapes metrics from all services via `/actuator/prometheus`
- **Grafana** (port 3000) — dashboards for visualization

> **Grafana quick start:** Import dashboard ID `10991` for RabbitMQ and `19004` for Spring Boot services.

---

## Architecture & Network Isolation

Docker networks are used to ensure each container can only communicate with the services it needs:

```
user-service       → user-database-network, rabbitmq-network, nginx-network, monitoring-network
stock-service      → stock-database-network, nginx-network, monitoring-network
email-service      → rabbitmq-network, monitoring-network
rabbitmq           → rabbitmq-network, monitoring-network
nginx              → nginx-network
prometheus         → rabbitmq-network, monitoring-network
grafana            → monitoring-network
pgadmin            → user-database-network, stock-database-network
```

The `email-service` has **no access** to any database network. The `stock-service` has **no access** to RabbitMQ. Databases are never exposed directly only through their respective service.

---

## API Payload Examples

All requests below go through Nginx at `http://localhost:8000`.

---

### Auth — user-api

#### POST `/auth/register`

```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

#### POST `/auth/login`

```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**

```json
{
  "email": "john@example.com",
  "token": "<access_token>",
  "refreshToken": "<refresh_token>"
}
```

#### POST `/auth/confirm`

```json
{
  "email": "john@example.com",
  "code": "123456"
}
```

#### POST `/auth/resend-code`

```json
{
  "email": "john@example.com"
}
```

#### POST `/auth/recover-password`

```json
{
  "email": "john@example.com"
}
```

#### POST `/auth/reset-password`

```json
{
  "email": "john@example.com",
  "code": "123456",
  "password": "newSecret123"
}
```

#### POST `/auth/change-password` _(requires Bearer token)_

```json
{
  "currentPassword": "secret123",
  "newPassword": "newSecret123"
}
```

#### POST `/auth/refresh`

```json
{
  "refreshToken": "<refresh_token>"
}
```

**Response:**

```json
{
  "accessToken": "<new_access_token>",
  "refreshToken": "<new_refresh_token>"
}
```

#### POST `/auth/logout`

```json
{
  "refreshToken": "<refresh_token>"
}
```

---

### Brands — stock-control-api _(requires Bearer token)_

#### POST `/api/v1/brand/` _(ADMIN)_

```json
{
  "name": "Rolex",
  "country": "Switzerland",
  "founded": 1905
}
```

#### GET `/api/v1/brand?pageNumber=1`

Optional query param: `founded=1905`

#### GET `/api/v1/brand/{id}`

#### PUT `/api/v1/brand/{id}` _(ADMIN)_

```json
{
  "name": "Rolex Updated",
  "country": "Switzerland",
  "founded": 1905
}
```

#### DELETE `/api/v1/brand/{id}` _(ADMIN)_

---

### Watches — stock-control-api _(requires Bearer token)_

#### POST `/api/v1/watch/` _(ADMIN)_

```json
{
  "brandId": "uuid-here",
  "model": "Submariner",
  "reference": "SUB-116610LN",
  "movimentType": "AUTOMATIC",
  "boxMaterial": "STEEL",
  "glassType": "SAPPHIRE",
  "waterResistanceM": 300,
  "diameterMm": 40.0,
  "lugToLugMm": 48.0,
  "thicknessMm": 12.5,
  "lugWidthMm": 20.0,
  "priceInCents": 950000,
  "image": "https://example.com/watch.jpg"
}
```

**Enum values:**

- `movimentType`: `QUARTZ`, `AUTOMATIC`, `MANUAL`
- `boxMaterial`: `STEEL`, `TITANIUM`, `RESIN`, `BRONZE`, `CERAMIC`
- `glassType`: `MINERAL`, `SAPPHIRE`, `ACRYLIC`

#### GET `/api/v1/watch`

All query params are optional:

| Param           | Type         | Example                                                        |
| --------------- | ------------ | -------------------------------------------------------------- |
| `pageNumber`    | int          | `1`                                                            |
| `pageSize`      | int (max 60) | `12`                                                           |
| `brand`         | string       | `rolex`                                                        |
| `movimentType`  | string       | `AUTOMATIC`                                                    |
| `boxMaterial`   | string       | `STEEL`                                                        |
| `glassType`     | string       | `SAPPHIRE`                                                     |
| `minResistance` | int          | `100`                                                          |
| `maxResistance` | int          | `300`                                                          |
| `minPrice`      | long         | `100000`                                                       |
| `maxPrice`      | long         | `1000000`                                                      |
| `minDiameter`   | float        | `38.0`                                                         |
| `maxDiameter`   | float        | `44.0`                                                         |
| `orderBy`       | string       | `price_asc`, `price_desc`, `diameter_asc`, `wr_desc`, `newest` |

#### GET `/api/v1/watch/{id}`

#### PUT `/api/v1/watch/{id}` _(ADMIN)_

Same body as POST.

#### DELETE `/api/v1/watch/{id}` _(ADMIN)_

---

## Tech Stack

| Layer            | Technology                             |
| ---------------- | -------------------------------------- |
| Language         | Java 17                                |
| Framework        | Spring Boot 4.x                        |
| Security         | Spring Security + JWT (auth0 java-jwt) |
| Database         | PostgreSQL 17                          |
| Migrations       | Flyway                                 |
| Messaging        | RabbitMQ 4.x                           |
| Email            | Resend                                 |
| Gateway          | Nginx                                  |
| Monitoring       | Prometheus + Grafana                   |
| Containerization | Docker + Docker Compose                |
