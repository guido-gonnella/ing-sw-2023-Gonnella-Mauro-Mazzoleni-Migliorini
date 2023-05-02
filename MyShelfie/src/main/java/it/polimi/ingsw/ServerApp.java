package it.polimi.ingsw;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.*;

import java.io.IOException;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args){

        int serverPort = 4999; //default value

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

        GameController gc = new GameController();
        Game game = new Game();
        game = gc.createGame();




    }
}
