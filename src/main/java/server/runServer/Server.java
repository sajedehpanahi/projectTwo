package server.runServer;

import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Server {

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

    public void ready(){
        //wait for terminals request
    }

    public void validation(){

    }

    public void sendResponseToTerminal(String terminalId){

    }


}
