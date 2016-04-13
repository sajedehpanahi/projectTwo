package terminal.run;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class TerminalFileManager {

    private String inputFilePath;
    private String logFilePath;
    private String responseFilePath;

    public TerminalFileManager(String inputFilePath, String logFilePath, String responseFilePath) {
        this.inputFilePath = inputFilePath;
        this.logFilePath = logFilePath;
        this.responseFilePath = responseFilePath;
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
                        serverInfo.setPort(Integer.parseInt(attributes.next().getValue().trim()));
                        serverInfo.setIp(attributes.next().getValue());
                    } else if ("outlog".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        //logFilePath = attributes.next().getValue();
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
        return  new Terminal(this , terminalId,terminalType,serverInfo,transactionList);
   }

    public void saveResponse(String transactionId, String responseMessage){

        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(responseFilePath);

            Node root=document.getFirstChild();

            Element newElement = document.createElement("response");

            newElement.setAttribute("id", transactionId);
            newElement.setAttribute("responseMessage", responseMessage);

            root.appendChild(newElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new File(responseFilePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLog(String info) {
        try {
            FileHandler fileHandler;
            Logger logger;
            logger = Logger.getLogger("TerminalLogFile");
//            FileHandler.
            fileHandler = new FileHandler(logFilePath , true );

            logger.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
  //          logger.info(info);
//            logger.log(Level.OFF,info);
            fileHandler.close();
        }catch (IOException e){
            System.out.println("Error Reading Terminal Log File!");
        }
    }
}
