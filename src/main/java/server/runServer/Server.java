package server.runServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Server extends Thread {

    int port;
//    String outLog;
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

        //while(true)
        //{
        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        Socket socket = serverSocket.accept();
        System.out.println("Just connected to " + socket.getRemoteSocketAddress());
        try {

            DataInputStream serverDataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream serverDataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {

                String inputStream = serverDataInputStream.readUTF();
                System.out.println(inputStream);
                if (inputStream == "end") {
                    break;
                }
                serverDataOutputStream.writeUTF("Response to terminal");
            }
                socket.close();

        } catch (SocketTimeoutException e) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
        }
    }

    public void validation(){

    }

    public void sendResponseToTerminal(String terminalId){

    }


}
