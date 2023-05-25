package it.polimi.ingsw;

import it.polimi.ingsw.Controller.InputController;
import it.polimi.ingsw.Network.ServerPack.Server;
import it.polimi.ingsw.View.View;

import java.util.Scanner;

/**
 * Class that runs the server
 */
public class ServerApp {
    public static void main(String[] args){

        int serverPort = 8080; //default value
        boolean socket = true;
        boolean gui = true;
        View view;

        Scanner s = new Scanner(System.in);
        System.out.println("Vuoi usare la gui? [s/n]");

        gui = (s.next()).equals("s");

        do {
            System.out.println("Select port [8080 default]");
            serverPort = s.nextInt();
        }while(!InputController.isValidPort(serverPort));

        Server server = new Server(serverPort);
        Thread thread = new Thread(server, "server_");
        thread.start();
    }
}
