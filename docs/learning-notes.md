Phase 1: Project Setup
What I did

Created Spring Boot project using IntelliJ

Used Java 17

Added dependencies:

Spring Web

Spring Data JPA

Spring Security

Thymeleaf

H2 Database

Why

Spring Boot is common in enterprise apps

H2 is easy for local testing

Thymeleaf keeps UI simple

Learning

Spring Security enables default login automatically

Default security must be disabled to demonstrate vulnerabilities

🏗️ Phase 2: Security Configuration (Temporary & Insecure)
What I did

Disabled Spring Security protections:

CSRF disabled

All endpoints permitted

Default login disabled

Frame options relaxed for H2 console

Why

To take full control of authentication logic

To intentionally create broken authentication

Learning

Spring Security blocks frames by default

H2 console needs frameOptions().sameOrigin()

Closing IDE does not stop backend services

🏗️ Phase 3: Database & Entity Setup
What I did

Created User entity

Used plain-text password field

Mapped entity to table USERS

Configured H2 in-memory database

Why

Plain-text passwords are required to demonstrate Broken Authentication

user is a reserved keyword → learned to avoid it

Learning

Reserved SQL keywords can break schema generation

In-memory DB resets on every restart

🏗️ Phase 4: H2 Console Access
What I did

Enabled H2 console at /h2-console

Connected using JDBC URL jdbc:h2:mem:testdb

Learning

Spring Security blocks H2 UI without frame config

DB visibility helps verify vulnerabilities

🏗️ Phase 5: Test Data (Intentionally Weak)
What I did

Inserted users manually:

user1 / password123

admin / admin123

Why

Weak credentials make exploitation obvious

Helps test authentication quickly

Learning

Restarting app wipes in-memory data

Login failures can be caused by empty DB, not code bugs

🏗️ Phase 6: Vulnerable Login Implementation
What I did

Created custom login.html

Implemented POST /login

Compared passwords using plain-text equality

Stored user object directly in session

Code behavior
user.getPassword().equals(password)

Why this is insecure

No hashing

No rate limiting

No account lockout

Weak session handling

Learning

Broken Authentication is often custom code

Framework defaults are safer than manual logic

🔓 Vulnerability Observed: Broken Authentication
What makes it broken

Plain-text passwords

Weak credentials

Unlimited login attempts

No audit or lockout

Confirmation

Login succeeds for both normal and admin users

No additional checks performed