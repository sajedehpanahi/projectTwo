package terminal.run;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class Terminal {

    String id;
    String type;
    ServerInfo serverInfo;
    List<Transaction> transactions;
    TerminalFileManager fileManager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Terminal(TerminalFileManager terminalFileManager , String id, String type, ServerInfo serverInfo, List<Transaction> transactions) {
        this.id = id;
        this.type = type;
        this.serverInfo = serverInfo;
        this.transactions = transactions;
        this.fileManager = terminalFileManager;
    }

    public void run() throws IOException {

        Socket terminalSocket = new Socket( serverInfo.getIp() , serverInfo.getPort());

        DataOutputStream terminalDataOutputStream = new DataOutputStream(terminalSocket.getOutputStream());
        DataInputStream terminalDataInputStream = new DataInputStream(terminalSocket.getInputStream());

        terminalDataOutputStream.writeUTF("with id: " + this.id + " and type: "+ this.type + " ");

        for( Transaction transaction : transactions) {
            terminalDataOutputStream.writeUTF(transaction.toStream());
            //System.out.println("Server says " + terminalDataInputStream.readUTF());
            String responseMessage = terminalDataInputStream.readUTF();
            System.out.println(responseMessage);
            fileManager.saveResponse(transaction.getId(), responseMessage);
            fileManager.getLog("transaction " + transaction.getId() + " in terminal " + this.id + " " + responseMessage);
        }
        terminalSocket.close();
    }
}


