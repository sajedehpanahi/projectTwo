package server.runServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class RunServer {
    public static void main(String[] args) {

        final ServerFileManager fileManager = new ServerFileManager();
        fileManager.parseInput();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Deposit> deposits = fileManager.getDepositList();
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Deposit deposit : deposits){
                        System.out.println(deposit.getInitialBalance());
                    }
                }
            }
        }).start();

        try (ServerSocket serverSocket = new ServerSocket(fileManager.getServerPort())) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new MultiThreadServer(socket , fileManager)).start();
            }
        } catch (IOException e){
            System.out.println("");
            e.printStackTrace();
        }
    }
}
