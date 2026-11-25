import java.util.ArrayList;
import java.util.List;

public class Account {
    protected String accountHolder;
    protected String accountNumber;
    protected double balance;
    protected List<String> transactionHistory;

    private static final Object TRANSFER_LOCK = new Object();

    public Account(String accountHolder, String accountNumber, double initialBalance) {
        if (accountHolder == null || accountNumber == null || !Double.isFinite(initialBalance) || initialBalance < 0) {
            throw new IllegalArgumentException("Account details and a finite non-negative initial balance are required.");
        }
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + initialBalance);
    }

    public void deposit(double amount) {
        if (Double.isFinite(amount) && amount > 0 && Double.isFinite(balance + amount)) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount + " | Balance: " + balance);
            System.out.println("Deposited " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (Double.isFinite(amount) && amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + " | Balance: " + balance);
            System.out.println("Withdrew " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Insufficient funds or invalid amount!");
        }
    }

    /**
     * Transfers funds atomically between two distinct accounts.
     * Both balances and histories are updated only after all validations pass.
     *
     * @return true when the transfer succeeds; false when it is rejected.
     */
    public static boolean transfer(Account source, Account destination, double amount) {
        synchronized (TRANSFER_LOCK) {
            if (source == null || destination == null || source == destination
                    || !Double.isFinite(amount) || amount <= 0
                    || amount > source.balance
                    || !Double.isFinite(destination.balance + amount)) {
                return false;
            }

            double sourceBalanceAfter = source.balance - amount;
            double destinationBalanceAfter = destination.balance + amount;
            if (!Double.isFinite(sourceBalanceAfter) || !Double.isFinite(destinationBalanceAfter)) {
                return false;
            }

            source.balance = sourceBalanceAfter;
            destination.balance = destinationBalanceAfter;
            source.transactionHistory.add("Transferred out: " + amount + " | Balance: " + source.balance);
            destination.transactionHistory.add("Transferred in: " + amount + " | Balance: " + destination.balance);
            return true;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for " + accountHolder + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
