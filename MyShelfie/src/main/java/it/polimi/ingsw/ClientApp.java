package it.polimi.ingsw;
import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Controller.NetworkHandlerTaskqueue;
import it.polimi.ingsw.View.Gui.Gui;
import it.polimi.ingsw.View.Gui.JavaFXGui;
import it.polimi.ingsw.View.Cli;
import javafx.application.Application;

import java.util.Objects;
import java.util.Scanner;

public class ClientApp {
    //public static it.polimi.ingsw.Network.SocketClient;

    public static void main(String[] args) {

        boolean cliView = false;
        boolean socketConnection = true;
        Scanner s = new Scanner(System.in);
        String sel;

        System.out.println("Cli or gui? [c/g]");
        cliView = !s.next().equals("g");
        System.out.print(("Want to use socket or RMI connection? [s/r]\n"));


        sel = s.next();
        while (!Objects.equals(sel, "s") && !Objects.equals(sel, "r")){
            System.out.print("The selection is not valid, please select [s] for socket\n or [r] for RMI\n");
            sel = s.next();
        }

        socketConnection = !sel.equals("r");

        if(cliView){
            Cli view = new Cli();
            NetworkHandler networkHandler = new NetworkHandler(view, socketConnection);
            view.addObserver(networkHandler);
            Thread thread= new Thread(networkHandler, "networkHandler_");
            thread.start();
        }else{
            Application.launch(JavaFXGui.class);
        }
    }
}