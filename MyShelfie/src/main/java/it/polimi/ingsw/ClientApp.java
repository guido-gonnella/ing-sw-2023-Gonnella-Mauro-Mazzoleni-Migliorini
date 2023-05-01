package it.polimi.ingsw;

import main.java.it.polimi.ingsw.View.Cli;

public class ClientApp {
    public static Cli gui;
    public static it.polimi.ingsw.Network.SocketClient

    public static void main(String[] args){

        boolean cliView = false; //default value

        for(String arg : args){
            if (arg.equals("--cli")) {
                cliView = true;
                break;
            }
        }

        if(cliView){
            gui= new Cli();

        }

        //da aggiungere per la GUI
        /*else{

        }*/
    }
}
