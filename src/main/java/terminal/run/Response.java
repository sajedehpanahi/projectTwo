package terminal.run;

/**
 * Created by DotinSchool2 on 4/12/2016.
 */
public class Response {
    private String transactionId;
    private String transactionType;
    private String transactionAmount;
    private String transactionDeposit;
    private String status;
    private String errorMessage;

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionDeposit() {
        return transactionDeposit;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Response() {
    }

    public Response(String transactionId, String transactionType, String transactionAmount, String transactionDeposit, String status, String errorMessage) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDeposit = transactionDeposit;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Response toResponse(String input){
        String[] elements = input.split("#");
        return new Response(elements[0],elements[1],elements[2],elements[3],elements[4],elements[5]);
    }

    public String ResponseToString(String input){
        String[] elements = input.split("#");
        return "transaction with id #"+elements[3]+elements[4]+ "with error message :" + elements[2];
    }

}
