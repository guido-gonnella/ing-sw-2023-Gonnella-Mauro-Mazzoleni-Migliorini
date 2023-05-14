package it.polimi.ingsw;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args){

        int serverPort = 8080; //default value
        boolean socket = true;

        for (int i = 0; i < args.length; i++) {
            if (args.length >= 2 && (args[i].equals("--port") || args[i].equals("-p"))) {
                try {
                    serverPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    PrintStream out = new PrintStream(System.out);
                    out.print("Error in the number format of the port");
                }
            }
            if (args[i].equals("--rmi")) {
                socket = false;
            }
        }

        GameController gameController = new GameController();
        Server server = new Server(gameController);

        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();

        /*ServerHandler serverHandler = new ServerHandler(serverPort);
        Thread thread = new Thread(serverHandler, "serverhandler_");
        thread.start();*/

        /*  questa roba servirà quando avremo implementato anche la GUI


        for(int i = 0; i<args.length; i++){
            if(args.length >= 2 && args[i].equals("--port")){
                try{
                    serverPort = Integer.parseInt(args[i+1]);
                }catch(NumberFormatException e){
                    System.out.println("Invalid port, using default port 4999");
                }
            }
        }*/
/*
        GameController gc = new GameController();
        Game game = new Game();
        game = gc.createGame();
*/



    }
}
