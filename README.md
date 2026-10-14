# Bank Account Simulation

[![Java](https://img.shields.io/badge/Java-OOP-orange?logo=openjdk)](https://www.java.com/) [![Repository](https://img.shields.io/badge/GitHub-Source-181717?logo=github)](https://github.com/kandisaikishore/BANK-ACCOUNT-SIMULATION) [![Last Commit](https://img.shields.io/github/last-commit/kandisaikishore/BANK-ACCOUNT-SIMULATION)](https://github.com/kandisaikishore/BANK-ACCOUNT-SIMULATION/commits/main)

A Java console project that simulates basic bank account behaviour to demonstrate classes, inheritance, method overriding, and transaction-style operations.

## Highlights

- Model a basic account and a savings-account specialization
- Demonstrate deposit and withdrawal workflows
- Explore account-specific behaviour and interest logic where implemented
- Practise object-oriented design with small, focused classes

## Tech stack

- Java / JDK
- Object-oriented programming
- Console-based interaction

## Prerequisites

Install a JDK and ensure `java` and `javac` are available.

## Run locally

```bash
git clone https://github.com/kandisaikishore/BANK-ACCOUNT-SIMULATION.git
cd BANK-ACCOUNT-SIMULATION
javac Account.java SavingsAccount.java Main.java
java Main
```

If files use packages or different class names, adjust the compile and run commands to match the source layout.

## Test checklist

Test valid and invalid deposits, withdrawals larger than the available balance, zero and negative amounts, and the account-specific behaviour. Use the repository's test source or add automated tests for these cases.

## Safety and scope

This is a programming exercise, not a banking product. It must not be used to handle real accounts, customer data, or money.

## Contributing

Report issues with reproducible steps. Include the validation you performed when submitting a change.

## License

No license file is currently declared. Add a `LICENSE` file to define reuse terms.
