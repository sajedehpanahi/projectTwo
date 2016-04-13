package server.exceptions;

/**
 * Created by DotinSchool2 on 4/13/2016.
 */
public class InvalidTransactionTypeException extends Exception {
    public InvalidTransactionTypeException(String transactionType){
        super("Transaction type '"+transactionType+"' is not defined in system");
    }
}
