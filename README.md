# Business Integration Service

A Spring Boot application that manages customer records and enriches business data through external API integrations.

Built as a portfolio project to demonstrate backend API development, data modeling, external integration, and PostgreSQL persistence.

## Tech Stack

- Kotlin
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Validation

## Features

- Create customer
- List customers
- View one customer by id
- Enrich customer with predicted age from external API
- Clean 404 handling when customer is not found

## API Endpoints

- `GET /customers` - list all customers
- `POST /customers` - create a customer
- `GET /customers/{id}` - view one customer
- `POST /customers/{id}/enrich` - enrich customer with external data

### Create a customer

```bash
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Test","lastName":"User","email":"test@example.com"}'
```

### List customers

```bash
curl http://localhost:8080/customers
```

### View one customer

```bash
curl http://localhost:8080/customers/1
```

### Enrich a customer

```bash
curl -X POST http://localhost:8080/customers/1/enrich
```

Enrichment calls `https://api.agify.io?name={firstName}`, reads the predicted `age`, and saves it as `predictedAge` on the same customer row.

## Getting Started

### Prerequisites

- Java 17
- PostgreSQL running locally on port 5432
- Maven - included via wrapper, no separate install needed

### 1. Create the database

```bash
psql -U postgres -h localhost -c "CREATE DATABASE business_integration;"
```

Tables are created automatically by Hibernate from the entity classes.

### 2. Configure the database connection

`src/main/resources/application.properties`:

```properties
spring.application.name=business-integration-service
spring.datasource.url=jdbc:postgresql://localhost:5432/business_integration
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

The password is not stored in code. Set it as an environment variable in your run configuration:

- Name: `DB_PASSWORD`
- Value: your local Postgres password

### 3. Run the app

Run `BusinessIntegrationServiceApplication` from IntelliJ, then open:

`http://localhost:8080/customers`

## Project Structure

- `Customer.kt` - JPA entity for `customers` table
- `CustomerRepository.kt` - database actions via `JpaRepository`
- `CustomerController.kt` - REST API endpoints
- `BusinessIntegrationServiceApplication.kt` - application entry point

## Customer Model

- `id` - auto-generated primary key
- `firstName` - required
- `lastName` - required
- `email` - required
- `predictedAge` - optional, filled by enrichment

## Run with Docker

```bash
docker compose up --build
```

## Future Improvements

- Add products as a second domain
- Add simple pricing / business rules
- Add integration tests
