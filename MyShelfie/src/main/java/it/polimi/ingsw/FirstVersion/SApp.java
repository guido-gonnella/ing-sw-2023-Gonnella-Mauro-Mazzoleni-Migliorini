package it.polimi.ingsw.FirstVersion;

import java.io.PrintStream;

public class SApp {
    public static void main( String[] args )
    {
        int serverPort = 8080;

        for (int i = 0; i < args.length; i++) {
            if (args.length >= 2 && (args[i].equals("--port") || args[i].equals("-p"))) {
                try {
                    serverPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    PrintStream out = new PrintStream(System.out);
                    out.print("Error in the number format of the port");
                }
            }
        }

        ServHand servHand = new ServHand(serverPort);
        Thread thread = new Thread(servHand, "servhand_");
        thread.start();
    }
}
