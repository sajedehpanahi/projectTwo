package terminal.run;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;


public class Terminal {

    String id;
    String type;
    ServerInfo server;
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

    public ServerInfo getServer() {
        return server;
    }

    public void setServer(ServerInfo serverInfo) {
        this.server = server;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Terminal(String id, String type, ServerInfo server, List<Transaction> transactionList) {
        this.id = id;
        this.type = type;
        this.server = server;
        this.transactionList = transactionList;
    }

    public void addTransaction(String id,String type,int amount,String deposit){
        transactionList.add(new Transaction(id,type,amount,deposit));
    }

    public void sendRequests(){

    }



}
