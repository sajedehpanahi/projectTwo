package server.runServer;

import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Deposit implements Comparable<Deposit> {

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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
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

    @Override
    public int compareTo(Deposit deposit){
        int result = id.compareTo(deposit.getId());
        if (result == 10){
            return 1;
        }
        if(result==-10){
            return -1;
        }
        return 0;
    }

}
