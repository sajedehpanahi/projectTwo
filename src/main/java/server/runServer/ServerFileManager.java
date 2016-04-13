package server.runServer;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
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
    int serverPort=0;
    List<Deposit> depositList = new ArrayList<Deposit>();

    public int getServerPort() {
        return serverPort;
    }

    public List<Deposit> getDepositList() {
        return depositList;
    }

    public void parseInput()  {
        try {
            FileReader fileReader = new FileReader(inputFilePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
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
            logFilePath = "src\\main\\resources\\"+(String)jsonObject.get("outLog");

        }catch (FileNotFoundException e) {
            System.out.println("Cannot Find core.json!");
        }catch (IOException e) {
            System.out.println("Error in Opening core.json!");
        }catch (ParseException e){
            System.out.println("Error in parsing core.json!");
        }
    }

    public synchronized void addToLogFile(String info) {
        try {
            FileHandler fileHandler;
            Logger logger;
            logger = Logger.getLogger("ServerLogFile");
            fileHandler = new FileHandler("src\\main\\resources\\ServerLogFile.log" , true);
            logger.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
//            logger.info(info);
//            logger.log(Level.OFF,info);
            fileHandler.close();
        }catch (IOException e){
            System.out.println("Error Reading Server Log File!");
        }
    }
}
