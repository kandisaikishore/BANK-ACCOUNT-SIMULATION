import java.util.ArrayList;
import java.util.List;

public class Account {
    protected static final Object TRANSACTION_LOCK = new Object();
    protected String accountHolder;
    protected String accountNumber;
    protected double balance;
    protected List<String> transactionHistory;

    public Account(String accountHolder, String accountNumber, double initialBalance) {
        if (!Double.isFinite(initialBalance) || initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance must be a finite, non-negative amount.");
        }
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + initialBalance);
    }

    public void deposit(double amount) {
        synchronized (TRANSACTION_LOCK) {
            if (Double.isFinite(amount) && amount > 0 && Double.isFinite(balance + amount)) {
                balance += amount;
                transactionHistory.add("Deposited: " + amount + " | Balance: " + balance);
                System.out.println("Deposited " + amount + ". New Balance: " + balance);
            } else {
                System.out.println("Invalid deposit amount!");
            }
        }
    }

    public void withdraw(double amount) {
        synchronized (TRANSACTION_LOCK) {
            if (Double.isFinite(amount) && amount > 0 && amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdrew: " + amount + " | Balance: " + balance);
                System.out.println("Withdrew " + amount + ". New Balance: " + balance);
            } else {
                System.out.println("Insufficient funds or invalid amount!");
            }
        }
    }

    /**
     * Transfers funds as one in-process transaction. Validation is completed before
     * either account is changed, and all account mutations share one lock.
     * @return true when the transfer is completed; false when it is rejected.
     */
    public boolean transferTo(Account destination, double amount) {
        synchronized (TRANSACTION_LOCK) {
            if (destination == null || destination == this || !Double.isFinite(amount)
                    || amount <= 0 || amount > balance
                    || !Double.isFinite(destination.balance + amount)) {
                return false;
            }

            balance -= amount;
            destination.balance += amount;
            transactionHistory.add("Transferred: " + amount + " to " + destination.accountNumber
                    + " | Balance: " + balance);
            destination.transactionHistory.add("Received: " + amount + " from " + accountNumber
                    + " | Balance: " + destination.balance);
            return true;
        }
    }

    public double getBalance() {
        synchronized (TRANSACTION_LOCK) {
            return balance;
        }
    }

    public void printTransactionHistory() {
        synchronized (TRANSACTION_LOCK) {
            System.out.println("\nTransaction History for " + accountHolder + ":");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}
