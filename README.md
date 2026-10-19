# CampusFind — Java + MySQL Lost & Found Portal

A beginner-friendly campus lost-and-found portal built with Java Spring Boot, Thymeleaf (HTML templates), CSS and MySQL.

## Requirements
- Java 17+
- Maven 3.8+
- MySQL 8+

## Run locally
1. Configure the MySQL username and password in `src/main/resources/application.properties`.
2. Start MySQL.
3. Run `mvn spring-boot:run` from the project root.
4. Open `http://localhost:8080`.

## Security warning
This is an educational starter, not production-ready. Before using real accounts or deploying publicly, enable Spring Security, hash passwords using BCrypt, add input validation and CSRF protection, and store credentials in environment variables.

## Deployment
A traditional Spring Boot application needs a Java-capable host and a managed MySQL database. Vercel's static hosting alone does not run an always-on Spring Boot server.
