package server.exceptions;

/**
 * Created by DotinSchool2 on 4/13/2016.
 */
public class InvalidCustomerInformationException extends Exception {
    public InvalidCustomerInformationException(String id){
        super("There is no customer with ID: " + id);
    }
}
