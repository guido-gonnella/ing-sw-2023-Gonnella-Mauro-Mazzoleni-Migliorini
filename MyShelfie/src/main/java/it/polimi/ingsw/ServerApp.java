package it.polimi.ingsw;

import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Network.ServerPack.Server;

import java.util.Objects;
import java.util.Scanner;

/**
 * Class that runs the server
 */
public class ServerApp {
    public static void main(String[] args){

        int serverPort; //default value
        boolean socket;
        String conn;

        Scanner s = new Scanner(System.in);
        do {
            System.out.print("Select port [8080 default]\n");
            serverPort = s.nextInt();
        }
        while(!NetworkHandler.isValidPort(serverPort));

        System.out.print("Using socket [s] or RMI [r] connection?\n");
        conn = s.next();
        while(!Objects.equals(conn, "s") && !Objects.equals(conn, "r")){
            System.out.print("Please select a valid input [s] [r]\n");
            conn = s.next();
        }
        socket = !conn.equals("r");

        Server server = new Server(serverPort, socket);
        Thread thread = new Thread(server, "server_");
        thread.start();
    }
}
