package server.runServer;

import server.exceptions.InvalidCustomerInformationException;
import server.exceptions.InvalidTransactionTypeException;
import server.exceptions.NotEnoughInitialBalanceException;
import server.exceptions.UpperBoundLimitException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class MultiThreadServer implements Runnable {

    private Socket socket;
    private ServerFileManager fileManager;

    MultiThreadServer(Socket socket, ServerFileManager serverFileManager) {
        this.socket = socket;
        this.fileManager = serverFileManager;
    }

    @Override
    public void run() {
        String terminalInfo="";
        try {
            DataInputStream serverDataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream serverDataOutputStream = new DataOutputStream(socket.getOutputStream());

            terminalInfo =serverDataInputStream.readUTF();
            fileManager.addToLogFile("Terminal " + terminalInfo + " connected to server");

            while (true) {
                String inputStream = serverDataInputStream.readUTF();
                String response = "";
                try {
                    validation(inputStream);
                    response = " Transaction Done Successfully!";
                } catch (InvalidCustomerInformationException e) {
                    response = e.getMessage();
                } catch (NotEnoughInitialBalanceException e) {
                    response = e.getMessage();
                } catch (UpperBoundLimitException e) {
                    response = e.getMessage();
                } catch (InvalidTransactionTypeException e) {
                    response = e.getMessage();
                }
                serverDataOutputStream.writeUTF(response);
                fileManager.addToLogFile("Terminal " + terminalInfo + "transaction information" + response);
            }
        } catch (IOException e) {
            fileManager.addToLogFile(" Terminal " + terminalInfo + " Disconnected");
            System.out.println(" Terminal Disconnected");
            try {
                socket.close();
                System.out.println(" Server Disconnected");
                fileManager.addToLogFile(" Server Disconnected from " + terminalInfo);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void validation(String inputStream) throws InvalidCustomerInformationException, NotEnoughInitialBalanceException, UpperBoundLimitException, InvalidTransactionTypeException {
        String[] inputElements = inputStream.split("#");

        Deposit deposit = findDepositById(inputElements[3]);
        doTransaction(deposit, inputElements[1], Integer.parseInt(inputElements[2].trim()));
    }

    public Deposit findDepositById(String id) throws InvalidCustomerInformationException {

        List<Deposit> depositList = fileManager.getDepositList();
        for (Deposit deposit : depositList) {
            if (deposit.getId().equals(id))
                return deposit;
        }
        throw new InvalidCustomerInformationException(id);
    }

    public void doTransaction(Deposit deposit, String transactionType, int amount) throws InvalidTransactionTypeException, UpperBoundLimitException, NotEnoughInitialBalanceException {
        if ("deposit".equalsIgnoreCase(transactionType)) {
            deposit.deposit(amount);
        } else if ("withdraw".equalsIgnoreCase(transactionType)) {
            deposit.withdraw(amount);
        } else {
            throw new InvalidTransactionTypeException(transactionType);
        }
    }

}
