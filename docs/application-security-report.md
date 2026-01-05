# Application Security Assessment Report
(Java Spring Boot – OWASP Top 10)

## Overview
This document describes the application security vulnerabilities that were intentionally introduced and then fixed in a Java Spring Boot web application. The purpose of this project was to gain hands-on experience in identifying, exploiting (conceptually), and remediating real-world application security issues aligned with the OWASP Top 10.

---

## 1. Broken Authentication
**OWASP Category:** A07 – Identification & Authentication Failures  
**Severity:** High

### Description
The application initially implemented custom authentication logic that stored user passwords in plain text and compared them directly during login. This approach provides no protection if credentials are leaked and allows easy account compromise.

### Where the Issue Existed
- Passwords stored in plain text in the database
- Login logic used direct string comparison

Vulnerable logic:
user.getPassword().equals(password)


### How I Identified It
- Reviewed login controller logic
- Inspected database contents via H2 console
- Verified successful login using weak credentials

### Exploitation Scenario
If an attacker gains access to the database (through misconfiguration, backups, or insider access), all user passwords are immediately exposed. This can lead to full account takeover, including admin accounts.

### Impact
- User and admin account compromise
- Credential reuse attacks
- No protection against brute-force attempts

### Fix Applied
- Implemented password hashing using BCrypt
- Replaced plain-text comparison with secure hash verification

Secure logic:
passwordEncoder.matches(rawPassword, storedHash)


### Why the Fix Works
- BCrypt uses salting and adaptive hashing
- Plain-text passwords are never stored
- Database compromise does not reveal credentials

### Git Reference
- Vulnerable: Initial authentication implementation
- Fixed: `Fix broken authentication by hashing passwords with BCrypt`

---

## 2. SQL Injection
**OWASP Category:** A03 – Injection  
**Severity:** High

### Description
The application used native SQL queries constructed via string concatenation with user input, allowing attackers to manipulate the SQL query structure.

### Where the Issue Existed

### Why the Fix Works
- BCrypt uses salting and adaptive hashing
- Plain-text passwords are never stored
- Database compromise does not reveal credentials

### Git Reference
- Vulnerable: Initial authentication implementation
- Fixed: `Fix broken authentication by hashing passwords with BCrypt`

---

## 2. SQL Injection
**OWASP Category:** A03 – Injection  
**Severity:** High

### Description
The application used native SQL queries constructed via string concatenation with user input, allowing attackers to manipulate the SQL query structure.

### Where the Issue Existed
SELECT * FROM USERS WHERE USERNAME = 'input'


### How I Identified It
- Reviewed repository layer using native queries
- Tested input such as `' OR '1'='1`
- Observed multiple records returned

### Exploitation Scenario
An attacker could bypass intended query logic and retrieve all user records, potentially exposing sensitive information.

### Impact
- Unauthorized data access
- Data leakage
- Possible escalation to authentication bypass

### Fix Applied
- Replaced string concatenation with parameterized native queries

Secure logic:

### How I Identified It
- Reviewed repository layer using native queries
- Tested input such as `' OR '1'='1`
- Observed multiple records returned

### Exploitation Scenario
An attacker could bypass intended query logic and retrieve all user records, potentially exposing sensitive information.

### Impact
- Unauthorized data access
- Data leakage
- Possible escalation to authentication bypass

### Fix Applied
- Replaced string concatenation with parameterized native queries

Secure logic:
SELECT * FROM USERS WHERE USERNAME = ?


### Why the Fix Works
- User input is treated as data, not executable SQL
- Query structure cannot be altered by attacker input

### Git Reference
- Fixed: `Fix SQL Injection using parameterized native queries`

---

## 3. Cross-Site Scripting (XSS)
**OWASP Category:** A03 – Injection  
**Severity:** Medium

### Description
User input was rendered back into the UI without output encoding, allowing arbitrary JavaScript execution in the browser.

### Where the Issue Existed
- Thymeleaf template used unescaped output rendering

Vulnerable usage:
th:utext


### How I Identified It
- Submitted script payloads via input fields
- Observed JavaScript execution in browser

### Exploitation Scenario
An attacker could execute malicious scripts to steal session data, deface pages, or perform phishing attacks.

### Impact
- Session hijacking
- Client-side attacks
- User trust compromise

### Fix Applied
- Replaced unescaped rendering with encoded output

Secure usage:
th:text


### Why the Fix Works
- HTML characters are escaped
- Browser treats input as text, not executable code

### Git Reference
- Fixed: `Fix XSS by encoding user input in Thymeleaf templates`

---

## 4. Broken Access Control
**OWASP Category:** A01 – Broken Access Control  
**Severity:** High

### Description
An admin endpoint was accessible to any authenticated user due to missing role-based authorization checks.

### Where the Issue Existed
- `/admin` endpoint validated login but not user role

### How I Identified It
- Logged in as a normal user
- Accessed admin endpoint directly via URL

### Exploitation Scenario
A low-privileged user could access admin-only functionality and sensitive data.

### Impact
- Privilege escalation
- Unauthorized access to admin features

### Fix Applied
- Implemented role-based access control
- Restricted admin endpoints to users with ADMIN role

### Why the Fix Works
- Authorization is enforced server-side
- User role is validated before granting access

### Git Reference
- Vulnerable: `Add vulnerable admin endpoint without role validation`
- Fixed: `Fix broken access control by enforcing role-based authorization`

---

## 5. Insecure Deserialization
**OWASP Category:** A08 – Software and Data Integrity Failures  
**Severity:** High

### Description
The application deserialized user-controlled input using Java’s ObjectInputStream without validation or restrictions.

### Where the Issue Existed
ObjectInputStream.readObject()

### How I Identified It
- Reviewed controller handling Base64 input
- Confirmed arbitrary object deserialization

### Exploitation Scenario
In real-world scenarios, attackers can send crafted serialized objects that execute code during deserialization, leading to remote code execution.

### Impact
- Potential remote code execution
- Full system compromise

### Fix Applied
- Completely disabled Java deserialization of user input

### Why the Fix Works
- Dangerous deserialization code path removed
- User input is no longer converted into executable objects

### Git Reference
- Fixed: `Fix insecure deserialization by disabling Java object deserialization`

---

## 6. Security Misconfiguration & Sensitive Logging
**OWASP Categories:**
- A05 – Security Misconfiguration
- A09 – Security Logging and Monitoring Failures

### Description
The application exposed internal tools and logged sensitive information.

### Issues Identified
- H2 database console publicly accessible
- User passwords printed in logs

### Impact
- Database exposure
- Credential leakage via logs

### Fix Applied
- Disabled H2 console by default
- Enabled H2 console only in dev profile
- Removed sensitive data from logs

### Why the Fix Works
- Debug tools are no longer exposed in production
- Logs do not leak credentials

### Git Reference
- Fixed: `Fix security misconfiguration and remove sensitive data from logs`

---

## Final Summary

This project demonstrates practical experience in:
- Identifying real-world application security vulnerabilities
- Understanding how vulnerabilities are exploited conceptually
- Applying secure coding practices to remediate risks
- Using Git history to track vulnerable and fixed states


---


