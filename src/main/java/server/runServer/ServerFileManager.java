package server.runServer;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class ServerFileManager {

    String inputFilePath = "src\\main\\resources\\core.json";
    String logFilePath;

    public Server parseInput() throws IOException, ParseException {

        int serverPort=0;
        List<Deposit> depositList = new ArrayList<Deposit>();

        FileReader fileReader = new FileReader(inputFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(fileReader);

        serverPort = Integer.parseInt(jsonObject.get("port").toString());

        JSONArray deposits = (JSONArray)jsonObject.get("deposits");
        for (Object deposit : deposits) {
            JSONObject jsonDeposit = (JSONObject)deposit;
            String customer = (String)jsonDeposit.get("customer");
            String id = (String)jsonDeposit.get("id");
            int initialBalance = Integer.parseInt((String)jsonDeposit.get("initialBalance"));
            int upperBound = Integer.parseInt((String)jsonDeposit.get("upperBound"));
            depositList.add(new Deposit(customer,id,initialBalance,upperBound));
        }

        logFilePath = (String)jsonObject.get("outLog");

        return new Server(serverPort,depositList);
    }

    public void addToLogFile(String info) throws IOException {
        FileHandler fileHandler;
        Logger logger;
        logger = Logger.getLogger("ServerLogFile");
        fileHandler = new FileHandler("src\\main\\resources\\ServerLogFile.log");
        logger.addHandler(fileHandler);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.info(info);
        logger.log(Level.OFF,info);
        fileHandler.close();

    }
}
