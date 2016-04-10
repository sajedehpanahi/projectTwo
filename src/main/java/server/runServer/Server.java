package server.runServer;

import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Server {

    String port;
    String outLog;
    List<Deposit> depositList;

    public void setPort(String port) {
        this.port = port;
    }

    public void ready(){
        //wait for terminals request
    }

    public void validation(){

    }

    public void sendResponseToTerminal(String terminalId){

    }


}
