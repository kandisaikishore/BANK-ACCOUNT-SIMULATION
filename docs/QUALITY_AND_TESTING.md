# Development and Testing Guide

This repository is a Java console exercise for object-oriented account behavior. Keep documentation aligned with the actual classes and methods; do not treat this simulator as a real banking system.

## Prerequisites and run

Install a JDK and verify `java -version` and `javac -version`. From the repository root:

```bash
javac Account.java SavingsAccount.java Main.java
java Main
```

If class names, package declarations, or source layout change, adjust the compile command to match the source. A successful compile does not prove the transaction rules are correct.

## Core behavior to validate

- Deposits with positive, zero, negative, and boundary amounts
- Withdrawals with sufficient and insufficient balance
- Invalid or missing account references, where supported
- Savings-account-specific rules and interest calculations, where implemented
- Repeated operations and invalid console input

## Balance invariants

For each accepted deposit or withdrawal, assert that the balance changes by the expected amount. Rejected operations must leave the balance unchanged. If transfers are added, debit and credit must be atomic: either both changes occur or neither occurs. Use decimal-safe arithmetic for money if monetary precision is part of the model.

## Testing approach

Prefer small unit tests around account operations. If no test framework is configured, first document a reproducible manual test matrix, then add a test framework as a separate, focused change. Test invalid inputs as well as successful operations.

## Limitations and safety

This is a learning simulator, not a production financial application. It does not provide regulated banking, secure customer identity, durable financial ledgers, transaction isolation, or compliance controls unless these are explicitly implemented and independently reviewed. Never use real account or customer data in tests.

## Pull-request checklist

- [ ] State the behavior being changed and provide a reproducible example.
- [ ] Add regression coverage for valid and rejected operations.
- [ ] Verify rejected operations do not mutate account state.
- [ ] Record compile/test commands and their results.
- [ ] Avoid unrelated changes and do not claim production banking readiness.
