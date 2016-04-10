package terminal.run;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class TerminalFileManager {

    private String inputFilePath = "src\\\\main\\\\resources\\\\terminal.xml";
    private String logFilePath;
    private String responseFilePath = "src\\\\main\\\\resources\\\\response.xml";
    private String terminalId;
    private String terminalType;
    private Server serverSettings;
    private List<Transaction> transactionList = new ArrayList<Transaction>();

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Server getServerSettings() {
        return serverSettings;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getResponseFilePath() {
        return responseFilePath;
    }

    public void setResponseFilePath(String responseFilePath) {
        this.responseFilePath = responseFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = "src\\\\main\\\\resources\\\\terminal.xml";
    }


    public void ParseInput() throws FileNotFoundException, XMLStreamException {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(inputFilePath));

        String transactionId="";
        String transactionType="";
        int transactionAmount=0;
        String transactionDeposit="";
        boolean hasTransaction=false;

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    Iterator<Attribute> attributes;
                    if ("terminal".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        terminalId = attributes.next().getValue();
                        terminalType = attributes.next().getValue();
                        System.out.println("start terminal");
                    } else if ("server".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        String port = attributes.next().getValue();
                        String ip = attributes.next().getValue();
                        serverSettings = new Server(ip, port);
                        System.out.println("start server");
                    } else if ("outlog".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        logFilePath = attributes.next().getValue();
                        System.out.println("start out log");
                    }else if ("transaction".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        transactionAmount = Integer.parseInt(attributes.next().getValue().trim());
                        System.out.println(transactionAmount);
                        transactionDeposit = attributes.next().getValue();
                        System.out.println(transactionDeposit);
                        transactionId = attributes.next().getValue();
                        System.out.println(transactionId);
                        transactionType = attributes.next().getValue();
                        System.out.println(transactionType);
                        hasTransaction = true;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("transaction") && hasTransaction){
                        transactionList.add(new Transaction(transactionId,transactionType,transactionAmount,transactionDeposit));
                        System.out.println("transaction added");
                    }
                    break;
            }
        }
    }

    public void addToLogFile(String logFilePath){

    }

    public void addToResponses(){

    }



}
