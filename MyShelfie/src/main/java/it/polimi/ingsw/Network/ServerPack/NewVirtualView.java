package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Shelf;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
import it.polimi.ingsw.Network.Message.S2C.UpdateShelfMessage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class that acts like a view for the server, it handles the "unpackaging"
 * and "packaging" of the messages from and to the client
 */
public class NewVirtualView {

    private Map<String, NewServerSocket> socketMap;

    /**
     * Constructor of the class
     */
    public NewVirtualView(){
        this.socketMap = null;
    }

    /**
     * Adds a client to the virualView
     * @param username of the user
     * @param serverSocket associated with the user
     */
    public void addClient(String username, NewServerSocket serverSocket){
        socketMap.put(username, serverSocket);
    }

    /**
     * Takes an input from the client, the possible types are:
     * SELECT_TILE_REQUEST
     * SELECT_COL_REQUEST
     * When the object is read by the gameController a cast type is necessary
     * @param user that have to send the input
     * @return the input of the client
     */
    /*TODO nel gameController inserire un try catch ogni volta che viene chiamato read per
    *  catchare ClassNotFoundException */
    public Object read(String user, MsgType message){
        NewServerSocket destinationClient = socketMap.get(user);
        //TODO aggiustare gli attributi e i metodi dei messaggi in modo che restituiscano i dati voluti
        /*switch(received.getMsgType()){
            case SELECT_TILE_REQUEST -> {
                destinationClient.sendMessage(new AskTileSelectMsg());
                Message received = destinationClient.readMessage();
                //TODO modifcare il messaggio che invia il client al server quando sceglie le tiles in modo che ci sia un ArrayList di coordinate come attributo e un metodo che lo restituisca
                return received.getCoordinates();
            }
            case SELECT_COL_REQUEST -> {
                destinationClient.sendMessage(new AskColumnSelectMsg());
                Message received = destinationClient.readMessage();
                return received.getCol();
            }
        }*/
    //TODO da rimuovere questo return, è temporaneo per non far dare errore
        return null;
    }

    /**
     * Sends an output to the client, the type of the message and its content
     * have to be already defined in the gameController
     * @param user that will read the output
     * @param message that the client will receive
     */
    //TODO rimuovere lo username tra i parametri dei messaggi, sono temporanei per non dare erorri
    public void write(String user, MsgType message, Object sendObject){
        NewServerSocket destinationClient = socketMap.get(user);
        switch(message){
            case BOARD_UPDATE -> {
                try {
                    destinationClient.sendMessage(new UpdateBoardMessage(user, (Board) sendObject));
                }catch (IOException e){
                    e.printStackTrace();
                    destinationClient.disconnect();
                }
            }
            case SHELF_UPDATE -> {
                try {
                    destinationClient.sendMessage(new UpdateShelfMessage(user, (Shelf) sendObject));
                }catch (IOException e){
                    e.printStackTrace();
                    destinationClient.disconnect();
                }
            }
        }

    }

    /**
     * Sends a message in broadcast to all clients
     * @param message that is sent in broadcast
     */
    public void writeBroadcast(Message message){
        try{
            for(NewServerSocket server : socketMap.values()){
                server.sendMessage(message);
            }
        }catch (IOException e){
            e.printStackTrace();
            for(NewServerSocket server : socketMap.values()){
                server.disconnect();
            }
        }
    }

    /**
     * To return the list of usernames
     * @return
     */
    public ArrayList<String> getUsernames(){
        return new ArrayList<>(socketMap.keySet());
    }

}
