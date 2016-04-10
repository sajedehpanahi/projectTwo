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


public class TerminalFileManager {

    private String inputFilePath = "src\\\\main\\\\resources\\\\terminal.xml";
    private String logFilePath;
    private String responseFilePath = "src\\\\main\\\\resources\\\\response.xml";

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


    public Terminal parseInput() throws FileNotFoundException, XMLStreamException {

        String terminalId ="";
        String terminalType ="";
        ServerInfo serverInfo = new ServerInfo();
        List<Transaction> transactionList = new ArrayList<Transaction>();

        String transactionId = "";
        String transactionType = "";
        int transactionAmount = 0;
        String transactionDeposit = "";
        boolean hasTransaction = false;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(inputFilePath));

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
                    } else if ("server".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        serverInfo.setIp(attributes.next().getValue());
                        serverInfo.setPort(attributes.next().getValue());
                    } else if ("outlog".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        logFilePath = attributes.next().getValue();
                    } else if ("transaction".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        transactionAmount = Integer.parseInt(attributes.next().getValue().trim().replace(",", ""));
                        transactionDeposit = attributes.next().getValue();
                        transactionId = attributes.next().getValue();
                        transactionType = attributes.next().getValue();
                        hasTransaction = true;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("transaction") && hasTransaction) {
                        //terminal.addTransaction(transactionId, transactionType, transactionAmount, transactionDeposit);
                        transactionList.add(new Transaction(transactionId,transactionType,transactionAmount,transactionDeposit));
                    }
                    break;
            }
        }
        return  new Terminal(terminalId,terminalType,serverInfo,transactionList);
   }

    public void addToLogFile(String logFilePath) {

    }

    public void addToResponses() {

    }


}
