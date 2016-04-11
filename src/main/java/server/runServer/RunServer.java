package server.runServer;

import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.IOException;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class RunServer {
    public static void main(String[] args) {

        ServerFileManager fileManager = new ServerFileManager();
        try {
            Server server = fileManager.parseInput();
            //System.out.println(server.depositList);
            server.ready();
        } catch (EOFException e){

        } catch (IOException e) {
            System.out.println("Input File Not Found!\n Or  server port problem");
            //e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Cannot Read Input JSON File!");
        }

    }
}
