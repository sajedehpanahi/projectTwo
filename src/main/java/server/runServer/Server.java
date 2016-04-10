package server.runServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        serverSocket = new ServerSocket(7070);
        serverSocket.setSoTimeout(10000);
        while(true)
        {
            try
            {
                System.out.println("Waiting for client on port " +serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "+ server.getRemoteSocketAddress());
                DataInputStream serverDataInputStream = new DataInputStream(server.getInputStream());
                System.out.println(serverDataInputStream.readUTF());
                DataOutputStream serverDataOutputStream =new DataOutputStream(server.getOutputStream());
                serverDataOutputStream.writeUTF("Thank you for connecting to "+ server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            }catch(SocketTimeoutException e)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public void validation(){

    }

    public void sendResponseToTerminal(String terminalId){

    }


}
