# Vulnerable → Secure Spring Boot Application (OWASP Top 10)

## Overview
This is a Java Spring Boot application built to **demonstrate common OWASP Top 10 vulnerabilities** and then **fix them step by step** using secure coding practices.

The purpose of this project is **hands-on learning in Application Security**, not just theory.

---

## Tech Stack
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- BCrypt (Spring Security Crypto)
- Maven

---

## OWASP Top 10 Coverage

| OWASP ID | Vulnerability | Status |
|--------|--------------|--------|
| A01 | Broken Access Control | Fixed |
| A02 | Cryptographic Failures | Fixed |
| A03 | Injection (SQL Injection, XSS) | Fixed |
| A05 | Security Misconfiguration | Fixed |
| A07 | Authentication Failures | Fixed |
| A08 | Insecure Deserialization | Fixed |
| A09 | Logging & Monitoring Failures | Addressed |

---

## Vulnerabilities Implemented and Fixed

### Broken Authentication
- Passwords stored in plain text
- Direct password comparison

**Fix**
- Passwords hashed using BCrypt
- Secure password comparison

---

### SQL Injection
- SQL queries built using string concatenation

**Fix**
- Parameterized native queries

---

### Cross-Site Scripting (XSS)
- User input rendered without output encoding

**Fix**
- Output encoded using Thymeleaf `th:text`

---

### Broken Access Control
- Admin endpoint accessible to any logged-in user

**Fix**
- Role-based authorization (`ADMIN` only)

---

### Insecure Deserialization
- Java deserialization of user-controlled input

**Fix**
- Disabled deserialization of untrusted data

---

### Security Misconfiguration
- H2 database console publicly accessible

**Fix**
- H2 console disabled by default
- Enabled only in `dev` profile

---

### Sensitive Data in Logs
- Credentials printed in logs

**Fix**
- Removed sensitive data from logging

---

## Git Workflow
- Initial commit contains intentionally vulnerable code
- Each vulnerability fixed in a separate commit
- Git history clearly shows before and after states

---

## Running the Application

### Secure Mode (default)
```bash
mvn spring-boot:run
```

### Dev Mode (H2 Console enabled)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Or in IntelliJ:
```
SPRING_PROFILES_ACTIVE=dev
```

---

## Test Credentials

| Role | Username | Password |
|-----|---------|----------|
| User | user1 | password123 |
| Admin | admin | admin123 |

(Passwords are stored as BCrypt hashes)

---

## Learning Outcome
This project helped me understand:
- How real-world security vulnerabilities appear in Java applications
- How to exploit and fix OWASP Top 10 issues
- Secure authentication and authorization
- Safe handling of user input
- Environment-based security configuration

---

## Note
This project is for **learning and demonstration purposes only**.
