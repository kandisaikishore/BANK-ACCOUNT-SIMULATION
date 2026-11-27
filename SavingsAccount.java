public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountHolder, String accountNumber, double initialBalance, double interestRate) {
        super(accountHolder, accountNumber, initialBalance);
        if (!Double.isFinite(interestRate) || interestRate < 0) {
            throw new IllegalArgumentException("Interest rate must be a finite, non-negative value.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    public void addInterest() {
        synchronized (TRANSACTION_LOCK) {
            double interest = balance * interestRate / 100;
            if (!Double.isFinite(interest) || !Double.isFinite(balance + interest)) {
                throw new IllegalStateException("Interest calculation exceeds the supported balance range.");
            }
            balance += interest;
            transactionHistory.add("Interest added: " + interest + " | Balance: " + balance);
            System.out.println("Interest of " + interest + " added. New Balance: " + balance);
        }
    }
}
