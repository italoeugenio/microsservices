# User API

This service handles user authentication and authorization within the microservices architecture.

---

## Database Schema

### Table: `tb_users`

Stores user information and authentication data.

| Column | Type | Description | Notes |
|:---|:---|:---|:---|
| `user_id` | `uuid` | Unique identifier for each user | **Primary Key** |
| `full_name` | `string` | Full name of the user | |
| `email` | `string` | User's email address, used for login | **Unique** |
| `password_hash` | `string` | Hashed password for authentication | |
| `role` | `string` | Role assigned to the user (`ADMIN`, `CLIENT`) | |
| `is_verified` | `boolean` | Whether the user has verified their email | Default: `false` |
| `verification_code` | `string` | 6-digit email verification code | Length: 6 |
| `verification_token_expires_at` | `timestamp` | Expiration time of the verification code | |
| `created_at` | `timestamp` | Timestamp when the account was created | |
| `updated_at` | `timestamp` | Timestamp when the account was last updated | |

---

### Table: `refresh_tokens` (via `RefreshTokenModel`)

Stores refresh tokens associated with each user.

| Column | Type | Description | Notes |
|:---|:---|:---|:---|
| `id` | `uuid` | Unique identifier for the token | **Primary Key** |
| `user_id` | `uuid` | Reference to the user who owns the token | **Foreign Key → tb_users** |

> Relationship: `tb_users` **1 — N** `refresh_tokens`  
> Cascade: `ALL` — tokens are deleted when the user is deleted.

---

## Roles & Authorities

| Role | Granted Authorities |
|:---|:---|
| `ADMIN` | `ROLE_ADMIN`, `ROLE_CLIENT` |
| `CLIENT` | `ROLE_CLIENT` |


---

## Tech Stack

- Java + Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- RabbitMQ (email messaging)
- Docker