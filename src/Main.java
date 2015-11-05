import indsys.filter.*;
import indsys.pipes.PipeImpl;import java.lang.String;import java.lang.System;import java.lang.Thread;

/**
 * Created by mod on 10/29/15.
 */
public class Main extends Thread{
    public static void main(String[] args) {
            (new Thread(new Main())).start();

    }
    public void run() {
        System.out.println("Hello from a thread!");
    }

}
