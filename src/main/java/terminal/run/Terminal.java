package terminal.run;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Terminal {

    String id;
    String Type;
    Server server;
    String outLogPath;
    List<Transaction> transactionList;

    public void connectToServer() throws FileNotFoundException, XMLStreamException {

        TerminalFileManager fileManager = new TerminalFileManager();
        fileManager.ParseInput();
        server = fileManager.getServerSettings();
        System.out.println("ip :"+ server.ip + " port :" + server.port);
    }

    public void sendRequests(){

    }



}
