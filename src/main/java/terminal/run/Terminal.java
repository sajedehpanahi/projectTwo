package terminal.run;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


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

    public void Run() throws IOException {

        Socket terminalSocket = new Socket( serverInfo.getIp() , serverInfo.getPort());

        DataOutputStream terminalDataOutputStream = new DataOutputStream(terminalSocket.getOutputStream());
        DataInputStream terminalDataInputStream = new DataInputStream(terminalSocket.getInputStream());

        TerminalFileManager fileManager = new TerminalFileManager();

        for( Transaction transaction : transactionList) {
            terminalDataOutputStream.writeUTF(transaction.toStream());
            //System.out.println("Server says " + terminalDataInputStream.readUTF());
            fileManager.saveResponse(terminalDataInputStream.readUTF());
           // terminalDataOutputStream.flush();
            fileManager.getLog("Response Received");
            fileManager.close();
        }
        terminalSocket.close();
    }




}


