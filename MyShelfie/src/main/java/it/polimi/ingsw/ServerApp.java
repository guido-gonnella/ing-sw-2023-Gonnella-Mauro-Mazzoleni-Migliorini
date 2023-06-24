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

        Scanner s = new Scanner(System.in);
        do {
            System.out.print("Select port [8080 default]\n");
            serverPort = s.nextInt();
        }
        while(!InputController.isValidPort(serverPort));

        System.out.print("Using socket connection? [y/n]\n");
        System.out.print("(The alternative is to use RMI)\n");
        socket = !s.next().equals("n");

        Server server = new Server(serverPort, socket);
        Thread thread = new Thread(server, "server_");
        thread.start();
    }
}
