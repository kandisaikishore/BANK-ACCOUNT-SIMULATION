public class AccountTest {
    static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    static void checkBalance(Account account, double expected, String message) {
        check(Math.abs(account.getBalance() - expected) < 0.000001, message);
    }

    public static void main(String[] args) {
        Account account = new Account("Alice", "1", 1000);
        account.deposit(250);
        checkBalance(account, 1250, "deposit");
        account.withdraw(200);
        checkBalance(account, 1050, "withdraw");
        account.withdraw(2000);
        account.deposit(Double.NaN);
        account.deposit(Double.POSITIVE_INFINITY);
        account.withdraw(Double.NEGATIVE_INFINITY);
        checkBalance(account, 1050, "rejected transactions must not change balance");

        boolean rejectedInitialBalance = false;
        try { new Account("Invalid", "0", Double.NaN); }
        catch (IllegalArgumentException expected) { rejectedInitialBalance = true; }
        check(rejectedInitialBalance, "non-finite initial balance must be rejected");

        SavingsAccount savings = new SavingsAccount("Bob", "2", 1000, 5);
        savings.addInterest();
        checkBalance(savings, 1050, "interest");

        Account source = new Account("Source", "3", 500);
        Account destination = new Account("Destination", "4", 100);
        check(source.transferTo(destination, 125), "valid transfer should succeed");
        checkBalance(source, 375, "source after transfer");
        checkBalance(destination, 225, "destination after transfer");
        check(Math.abs((source.getBalance() + destination.getBalance()) - 600) < 0.000001,
                "successful transfer must conserve total balance");

        check(!source.transferTo(destination, 1000), "insufficient-funds transfer must fail");
        check(!source.transferTo(null, 10), "null destination must fail");
        check(!source.transferTo(source, 10), "self-transfer must fail");
        check(!source.transferTo(destination, 0), "zero transfer must fail");
        check(!source.transferTo(destination, Double.NaN), "NaN transfer must fail");
        checkBalance(source, 375, "failed transfer must preserve source balance");
        checkBalance(destination, 225, "failed transfer must preserve destination balance");

        System.out.println("Bank tests passed");
    }
}
