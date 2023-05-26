package it.polimi.ingsw;
import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import it.polimi.ingsw.View.Cli;
import javafx.application.Application;

import java.util.Scanner;

public class ClientApp {
    //public static it.polimi.ingsw.Network.SocketClient;

    public static void main(String[] args) {

        boolean cliView = false;
        Scanner s = new Scanner(System.in);

        System.out.println("Cli or gui? [c/g]");
        cliView = !s.next().equals("g");

        if(cliView){
            Cli view = new Cli();
            NetworkHandler networkHandler = new NetworkHandler(view);
            view.addObserver(networkHandler);
            view.init();
        }else{
            Application.launch(JavaFXGui.class);
        }
    }
}
