package terminal.run;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DotinSchool2 on 4/13/2016.
 */
public class RunMultipleTerminals  {
    public static void main(String[] args) throws InterruptedException {
//        List<Thread> threads = new ArrayList<>();
//        for(int i=0 ; i< 3; i++){
//            threads.add(new Thread(new RunTerminal()));
//        }
//
//        for(Thread thread : threads){
//            thread.start();
//        }
//
//
//        for(Thread thread : threads){
//            thread.join();
//        }
        int maxNumberOfThreads=3;

        for(int i=0 ; i< maxNumberOfThreads; i++){
            new Thread(new RunTerminal(i)).start();
        }


    }
}
