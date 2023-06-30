package it.polimi.ingsw;

import it.polimi.ingsw.Controller.NetworkHandlerTaskqueue;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import it.polimi.ingsw.View.Cli;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import javafx.application.Application;
public class ClientApp {

    public static void main(String[] args) {

        boolean cliView = true;
        boolean socketConnection = true;

        for (String arg : args) {
            if (arg.equals("--gui") || arg.equals("-g"))
                cliView = false;
            if (arg.equals("--rmi") || arg.equals("-r"))
                socketConnection = false;
        }


        if(socketConnection){
            System.out.println("Socket Connection Selected");
        } else {
            System.out.println("RMI Connection Selected");
        }


        if(cliView){
            System.out.println("Cli Selected");
            Cli view = new Cli();
            NetworkHandlerTaskqueue networkHandler = new NetworkHandlerTaskqueue(view, socketConnection);
            view.addObserver(networkHandler);
            Thread thread= new Thread(networkHandler, "networkHandler_");
            thread.start();
        }else{
            System.out.println("Gui Selected");
            System.setProperty("prism.allowhidpi", "false");
            Application.launch(JavaFXGui.class);
        }
    }
}