# WinWin Travel – Backend Test Task

This repository contains two Spring Boot services (`auth-api` and `data-api`) and a Postgres database, orchestrated with Docker Compose.
The goal: authentication and protected text processing across two microservices.

---

## Prerequisites

* Docker & Docker Compose installed
* Java 17+ and Maven (only if you want to build locally)

---

## Project Structure

```
/auth-api       # Service A: authentication + protected /process
/data-api       # Service B: text transformation
docker-compose.yml
README.md
```

---

## Build & Run

1. Build both services:

```bash
mvn -f auth-api/pom.xml clean package -DskipTests
mvn -f data-api/pom.xml clean package -DskipTests
```

2. Start everything:

```bash
docker compose up -d --build
```

This will start:

* Postgres (on internal network)
* `auth-api` at [http://localhost:8080](http://localhost:8080)
* `data-api` at [http://localhost:8081](http://localhost:8081)

---

## Environment Variables

Defined in `docker-compose.yml`:

* `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASSWORD`
* `JWT_SECRET` – secret key for JWT signing
* `INTERNAL_TOKEN` – shared token for service-to-service communication

---

## Usage

### 1. Register

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"a@a.com","password":"pass"}'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"a@a.com","password":"pass"}'
```

Copy the token from response.

### 3. Process Text

```bash
curl -X POST http://localhost:8080/api/process \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"text":"hello"}'
```

Expected response:

```json
{ "result": "transformed text" }
```

A record will also be saved into the `processing_log` table in Postgres.

---

## Acceptance Criteria

* `register`, `login`, and `process` work via localhost with `docker compose up`
* Service B rejects requests without valid `X-Internal-Token`
* Service A stores logs in Postgres
