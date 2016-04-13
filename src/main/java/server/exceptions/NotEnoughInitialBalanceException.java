package server.exceptions;

/**
 * Created by DotinSchool2 on 4/13/2016.
 */
public class NotEnoughInitialBalanceException extends Exception {
    public NotEnoughInitialBalanceException(int initialBalance){
        super("Initial Balance is less than " + initialBalance);
    }
}
