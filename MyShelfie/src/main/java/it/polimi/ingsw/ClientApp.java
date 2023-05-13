package it.polimi.ingsw;
import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.View.PlayerView;
import it.polimi.ingsw.View.Cli;

import java.io.IOException;
import java.net.Socket;

public class ClientApp {
    //public static it.polimi.ingsw.Network.SocketClient;

    public static void main(String[] args) {

        boolean cliView = false;

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliView = true;
                break;
            }
            /*
            if (arg.equals("--rmi")){
                //set connection to rmi
            }
            */
        }

        if(cliView){
            Cli view = new Cli();
            ClientController clientController = new ClientController(view);
            view.addObserver(clientController);
            view.init();
        }

        /*  questa roba servirà quando avremo implementato anche la GUI

        boolean cliView = false; //default value

        for(String arg : args){
            if (arg.equals("--cli")) {
                cliView = true;
                break;
            }
        }

        if(cliView){
            gui= new Cli();

        }*/

        //da aggiungere per la GUI
        /*else{

        }*/
/*
        Player pl = new Player(PlayerView.takeNickname());
        PlayerView plv = new PlayerView(pl);
        String address = plv.takeAddress();
        int port = plv.takePort();
        SocketClient socketC = new SocketClient(pl);
        Socket socket = socketC.clientConnection(address, port);

*/
    }
}
