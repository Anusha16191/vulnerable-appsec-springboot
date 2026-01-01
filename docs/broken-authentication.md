Vulnerability: Broken Authentication
OWASP Category: A07 – Identification & Authentication Failures

Description:
The application authenticates users using plain-text password comparison
and stores credentials without hashing.

Detection:
Manual code review of authentication logic and database schema.

Impact:
An attacker with database access or brute-force capability can compromise
user accounts, including admin accounts.

Vulnerable Code:
user.getPassword().equals(password)

Risk:
High – leads to full account takeover.
