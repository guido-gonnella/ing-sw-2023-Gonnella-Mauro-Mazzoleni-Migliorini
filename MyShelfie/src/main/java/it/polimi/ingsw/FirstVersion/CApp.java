package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli;

import java.io.PrintStream;

public class CApp {
    public static void main( String[] args ){
        boolean cliView = false;
        int port = 8080;

        for (int i = 0; i < args.length; i++) {
            if (args.length >= 2 && (args[i].equals("--port") || args[i].equals("-p"))) {
                try {
                    port = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    PrintStream out = new PrintStream(System.out);
                    out.print("Error in the number format of the port");
                }
                if (args[i].equals("--cli") || args[i].equals("-c"))
                    cliView = true;
            }
        }
        if(cliView){
            Cli view = new Cli();
            String address = view.askAddress();
            SocketClien socketClien = new SocketClien(address, port);
            Thread thread = new Thread(Cli, "cli_");
            thread.start();
        }
    }
}
