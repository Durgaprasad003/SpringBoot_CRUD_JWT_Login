# 🔐 Product Management REST API with JWT Authentication & Role-Based Authorization

A secure **Spring Boot REST API** for product management with **JWT Authentication**, **Role-Based Authorization**, **Global Exception Handling**, and clean layered architecture.

## Features

* JWT Authentication (Access Token + Refresh Token)
* Role-Based Access Control (`USER`, `ADMIN`)
* Secure Spring Security Configuration
* CRUD Operations for Products
* Global Exception Handling
* Request Validation using `@Valid`
* Layered Architecture (Controller, Service, DAO, Security)
* Standard API Response Wrapper
* BCrypt Password Hashing
* Stateless Session Management

---

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT (JJWT)
* Hibernate
* MySQL / PostgreSQL (configurable)
* Lombok
* Maven

---

## Authentication & Authorization

### Roles

* `ADMIN`

  * Create product
  * Update product
  * Patch product
  * Delete product

* `USER`

  * View products

### Public Endpoints

* `/auth/**`
* `GET /products/get`
* `GET /products/{id}`

### Protected Endpoints

All other endpoints require authentication.

---

## Security Highlights

* JWT token validation through custom filter
* Passwords hashed using BCrypt
* Custom `401 Unauthorized` handler
* Custom `403 Forbidden` handler
* Stateless API design

---

## Product API Endpoints

### Create Product (ADMIN)

```http
POST /products/save
```

### Update Product (ADMIN)

```http
PUT /products/update/{id}
```

### Partial Update Product (ADMIN)

```http
PATCH /products/{id}
```

### Get All Products (Public)

```http
GET /products/get
```

### Get Product By Id (Public)

```http
GET /products/{id}
```

### Delete Product (ADMIN)

```http
DELETE /products/{id}
```

---

## Sample Product Request Body

```json
{
  "name": "Laptop",
  "price": 55000,
  "description": "Gaming laptop"
}
```

---

## Authentication Flow

### Login

```http
POST /auth/login
```

Returns:

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

### Use Token

```http
Authorization: Bearer your_access_token
```

---

## Validation & Exception Handling

Handled globally using `@RestControllerAdvice`

Examples:

* Invalid product ID
* Empty request body
* Negative price
* Unauthorized access
* Forbidden access
* Validation errors

---

## Project Structure

```text
src/main/java/com/learningstage/demo
│── ProductController
│── ProductService
│── DAO
│── Security
│── Entity
│── Dto
│── Exceptions
```

---

## How to Run

### Clone Project

```bash
git clone <your-repository-url>
cd <project-folder>
```

### Configure Database

Update:

```properties
application.properties
```

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
jwt.secretkey=
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Why This Project Matters

This project demonstrates real backend skills:

* REST API development
* Authentication & Authorization
* Secure coding practices
* Exception handling
* Clean architecture
* Production-style backend structure

---

## Future Improvements

* Swagger / OpenAPI Documentation
* Docker Deployment
* Unit Testing / Integration Testing
* Redis Token Blacklisting
* Email Verification
* Pagination & Sorting
* CI/CD Pipeline

---

## Author

Developed by **Durga Prasad**

Open to backend developer opportunities.
