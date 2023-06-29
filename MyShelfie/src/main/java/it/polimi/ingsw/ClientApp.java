package it.polimi.ingsw;

import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.View.Cli;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import javafx.application.Application;
public class ClientApp {

    public static void main(String[] args) {

        boolean cliView;
        boolean socketConnection;

        cliView = !args[0].equals("g");
        socketConnection = !args[1].equals("r");
        if(socketConnection){
            System.out.println("Socket Connection Selected");} else {
            System.out.println("RMI Connection Selected");
        }


        if(cliView){
            Cli view = new Cli();
            NetworkHandler networkHandler = new NetworkHandler(view, socketConnection);
            view.addObserver(networkHandler);
            Thread thread= new Thread(networkHandler, "networkHandler_");
            thread.start();
        }else{
            System.out.println("Cli Selected");
            Application.launch(JavaFXGui.class);
        }
    }
}