package server.runServer;


import server.exceptions.NotEnoughInitialBalanceException;
import server.exceptions.UpperBoundLimitException;

public class Deposit {

    String customer;
    String id;
    int initialBalance;
    int upperBound;

    public Deposit(String customer, String id, int initialBalance, int upperBound) {
        this.customer = customer;
        this.id = id;
        this.initialBalance = initialBalance;
        this.upperBound = upperBound;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "customer='" + customer + '\'' +
                ", id='" + id + '\'' +
                ", initialBalance=" + initialBalance +
                ", upperBound=" + upperBound +
                '}';
    }

    public synchronized void deposit(int amount) throws UpperBoundLimitException {
        if (initialBalance + amount > upperBound) {
            throw new UpperBoundLimitException(upperBound);
        }
        initialBalance=initialBalance + amount;
    }

    public synchronized void withdraw(int amount) throws NotEnoughInitialBalanceException {
        if (initialBalance < amount) {
            throw new NotEnoughInitialBalanceException(initialBalance);
        }
        initialBalance=initialBalance - amount;
    }

    public int getInitialBalance() {
        return initialBalance;
    }
}
