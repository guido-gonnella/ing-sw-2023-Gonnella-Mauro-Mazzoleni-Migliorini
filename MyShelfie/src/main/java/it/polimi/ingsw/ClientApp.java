package it.polimi.ingsw;
import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import it.polimi.ingsw.View.Cli;
import javafx.application.Application;

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
            NetworkHandler networkHandler = new NetworkHandler(view);
            view.addObserver(networkHandler);
            view.init();
        }else{
            Application.launch(JavaFXGui.class);
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
