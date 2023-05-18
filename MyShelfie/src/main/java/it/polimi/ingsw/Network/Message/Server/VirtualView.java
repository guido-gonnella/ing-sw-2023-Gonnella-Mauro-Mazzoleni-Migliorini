package it.polimi.ingsw.Network.Message.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

import java.io.IOException;
import java.util.Map;

/**
 * Class that acts like a view for the server, it handles the "unpackaging"
 * and "packaging" of the messages from and to the client
 */
public class VirtualView {

    private Map<String, NewServerSocket> socketMap;

    /**
     * Constructor of the class
     */
    public VirtualView(){
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
     * SELECT_TILE
     * HAND_TILE_SWAP
     * SELECT_COL
     * @param user that have to send the input
     * @return the input of the client
     */
    public Object read(String user){
        Message received = socketMap.get(user).readMessage();
        //TODO aggiustare gli attributi e i metodi dei messaggi in modo che restituiscano i dati voluti
        /*switch(received.getMsgType()){
            case SELECT_TILE -> {
                return received.getTiles();
            }
            case HAND_TILE_SWAP -> {
                return received.getTiles();
            }
            case SELECT_COL -> {
                return received.getColumn();
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
    public void write(String user, Message message){
        try {
            socketMap.get(user).sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
            socketMap.get(user).disconnect();
        }
    }
}
