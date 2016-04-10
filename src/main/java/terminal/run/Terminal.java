package terminal.run;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class Terminal {

    String id;
    String type;
    ServerInfo serverInfo;
    List<Transaction> transactionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = this.serverInfo;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Terminal(String id, String type, ServerInfo serverInfo, List<Transaction> transactionList) {
        this.id = id;
        this.type = type;
        this.serverInfo = serverInfo;
        this.transactionList = transactionList;
    }

    public void addTransaction(String id,String type,int amount,String deposit){
        transactionList.add(new Transaction(id,type,amount,deposit));
    }

    public void connectToServer() throws IOException {

        //System.out.println("Connecting to " + serverInfo.getIp() +" on port " + serverInfo.getPort());
        Socket terminalSocket = new Socket( serverInfo.getIp() , 7070);
        //System.out.println("Just connected to " + terminalSocket.getRemoteSocketAddress());
        OutputStream terminalOutputStream = terminalSocket.getOutputStream();
        DataOutputStream terminalDataOutputStream = new DataOutputStream(terminalOutputStream);
        terminalDataOutputStream.writeUTF("Hello from "+ terminalSocket.getLocalSocketAddress());
        InputStream terminalInputStream = terminalSocket.getInputStream();
        DataInputStream terminalDataInputStream = new DataInputStream(terminalInputStream);
        System.out.println("Server says " + terminalDataInputStream.readUTF());
        terminalSocket.close();

    }

    public void sendRequests(){

    }



}
