package it.polimi.ingsw;

public class ClientApp {
    public static void main(String[] args){

        boolean cliView = false; //default value

        for(String arg : args){
            if (arg.equals("--cli")) {
                cliView = true;
                break;
            }
        }

        if(cliView){

        }

        //da aggiungere per la GUI
        /*else{

        }*/
    }
}
