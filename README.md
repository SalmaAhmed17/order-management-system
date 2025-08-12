# order-management-system
# Overview
This repository contains an Order Management System (OMS) that manages Customers, Products, Orders, and Invoices via REST APIs (Spring Boot). The system includes JWT-based authentication and role-based access to order management endpoints. Docker is used for containerized deployment of both backend and database, enabling quick setup.

# Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- PostgreSQL as a DB engine
- Spring Security with JWT
- Maven
- Swagger (springdoc-openapi)
- Docker 

# Features
- CRUD for Customers, Products, Orders, and Invoices  
- Validations: stock availability, email/mobile format, required fields  
- Business rules: invoice auto-generation when order status becomes `COMPLETED`  
- Inquiry APIs: get orders by customer, get invoice by order, product filters with pagination & sorting  
- Exception handling for invalid token, missing fields, insufficient stock, and general errors  


# Docker Setup
The project includes a Dockerfile for the backend and a docker-compose.yml file to run the backend and database together.
Run with Docker Compose:
         - docker-compose up --build

- Backend will be available at: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
