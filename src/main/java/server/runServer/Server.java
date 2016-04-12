package server.runServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Server extends Thread {

    int port;
    List<Deposit> depositList;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public Server(int port, List<Deposit> depositList) {
        this.port = port;
        this.depositList = depositList;
    }

    public void ready() throws IOException {

        ServerSocket serverSocket;
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);

        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        Socket socket = serverSocket.accept();
        System.out.println("Just connected to " + socket.getRemoteSocketAddress());
        try {

            DataInputStream serverDataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream serverDataOutputStream = new DataOutputStream(socket.getOutputStream());

            ServerFileManager fileManager = new ServerFileManager();
            while (serverDataInputStream.available() >= 0) {

                String inputStream = serverDataInputStream.readUTF();
                //System.out.println(inputStream);
                String response = validation(inputStream);
                serverDataOutputStream.writeUTF(response);
                fileManager.addToLogFile("response sent");
            }
            socket.close();

        } catch (SocketTimeoutException e) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
        }
    }

    public String validation(String inputStream) {
        String[] inputElements = inputStream.split("#");
        String response = inputElements[0] + "#" + inputElements[1] + "#" + inputElements[2] + "#"+ inputElements[3] + "#";
        for (Deposit deposit : depositList) {
            if (deposit.getId().equalsIgnoreCase(inputElements[3])) {
                if ("deposit".equalsIgnoreCase(inputElements[1])) {
                    if (deposit.getInitialBalance() + Integer.parseInt(inputElements[2].trim()) < deposit.getUpperBound()) {
                        deposit.setInitialBalance(deposit.getInitialBalance() + Integer.parseInt(inputElements[2].trim()));
                        response += "Done#no error";
                        break;

                    } else {
                        System.out.println("error in transaction " + inputElements[0] + ": upper bound limit!");
                        response += "failed#upper bound limit!";
                        break;
                    }
                } else if ("withdraw".equalsIgnoreCase(inputElements[1])) {
                    if (deposit.getInitialBalance() > Integer.parseInt(inputElements[2].trim())) {
                        deposit.setInitialBalance(deposit.getInitialBalance() - Integer.parseInt(inputElements[2].trim()));
                        response += "Done#no error";
                        break;
                    } else {
                        System.out.println("error in transaction " + inputElements[0] + ": not enough initial balance!");
                        response += "failed#not enough initial balance!";
                        break;
                    }
                } else {
                    System.out.println("error in transaction " + inputElements[0] + ": invalid transaction type!");
                    response += "failed#invalid transaction type!";
                    break;
                }
            } else {
                System.out.println("error in transaction " + inputElements[0] + ": invalid customer information!");
                response += "failed#invalid customer information!";
                break;
            }
        }
        return response;
    }

    public void getLog(String info) {

        Logger logger = Logger.getLogger("ServerLogFile");
        FileHandler fileHandler;
        try {

            fileHandler = new FileHandler("src\\main\\resources\\ServerLogFile.log");
            logger.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            logger.info(info);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
