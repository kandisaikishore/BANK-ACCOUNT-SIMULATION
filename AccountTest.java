public class AccountTest {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void checkBalance(Account account, double expected, String message) {
        check(Math.abs(account.getBalance() - expected) < 0.000001,
                message + ": expected " + expected + ", got " + account.getBalance());
    }

    public static void main(String[] args) {
        Account account = new Account("Alice", "1", 1000);
        account.deposit(250);
        checkBalance(account, 1250, "deposit");
        account.withdraw(200);
        checkBalance(account, 1050, "withdraw");
        account.withdraw(2000);
        checkBalance(account, 1050, "overdraft rejected");

        SavingsAccount savings = new SavingsAccount("Bob", "2", 1000, 5);
        savings.addInterest();
        checkBalance(savings, 1050, "interest");

        Account sender = new Account("Sender", "3", 500);
        Account recipient = new Account("Recipient", "4", 100);
        double totalBefore = sender.getBalance() + recipient.getBalance();

        check(Account.transfer(sender, recipient, 125), "valid transfer should succeed");
        checkBalance(sender, 375, "sender after transfer");
        checkBalance(recipient, 225, "recipient after transfer");
        check(sender.getBalance() + recipient.getBalance() == totalBefore,
                "successful transfer must conserve total balance");

        check(!Account.transfer(sender, recipient, 1000), "insufficient funds should fail");
        checkBalance(sender, 375, "sender unchanged after insufficient funds");
        checkBalance(recipient, 225, "recipient unchanged after insufficient funds");

        check(!Account.transfer(sender, null, 10), "null destination should fail");
        check(!Account.transfer(sender, sender, 10), "same-account transfer should fail");
        check(!Account.transfer(sender, recipient, 0), "zero transfer should fail");
        check(!Account.transfer(sender, recipient, -1), "negative transfer should fail");
        check(!Account.transfer(sender, recipient, Double.NaN), "NaN transfer should fail");
        check(!Account.transfer(sender, recipient, Double.POSITIVE_INFINITY),
                "infinite transfer should fail");
        checkBalance(sender, 375, "sender unchanged after rejected transfers");
        checkBalance(recipient, 225, "recipient unchanged after rejected transfers");

        System.out.println("Bank tests passed");
    }
}
