package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Network.Message.C2S.SelectColumnMessage;
import it.polimi.ingsw.Network.Message.C2S.SelectTileMessage;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Shelf;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.S2C.AskColumnSelectMsg;
import it.polimi.ingsw.Network.Message.S2C.AskTileSelectMsg;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
import it.polimi.ingsw.Network.Message.S2C.UpdateShelfMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that acts like a view for the server, it handles the "unpackaging"
 * and "packaging" of the messages from and to the client
 */
public class VirtualView {

    private Map<String, SocketServer> socketMap;

    /**
     * Constructor of the class
     */
    public VirtualView(){
        this.socketMap = new HashMap<>();
    }

    /**
     * Adds a client to the virualView
     * @param username of the user
     * @param serverSocket associated with the user
     */
    public void addClient(String username, SocketServer serverSocket){
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
        SocketServer destinationClient = socketMap.get(user);
        switch(message){
            case SELECT_COL_REQUEST -> {
                return askCol(destinationClient);
            }
            case SELECT_TILE_REQUEST -> {
                return askCoords(destinationClient);
            }
            default -> {return null;}
        }
    }

    /**
     * Sends an output to the client, the type of the message and its content
     * have to be already defined in the gameController
     * @param user that will read the output
     * @param message that the client will receive
     */
    public void write(String user, MsgType message, Object sendObject){
        SocketServer destinationClient = socketMap.get(user);
        switch(message){
            case BOARD_UPDATE -> {
                destinationClient.sendMessage(new UpdateBoardMessage((Board) sendObject));
            }
            case SHELF_UPDATE -> {
                destinationClient.sendMessage(new UpdateShelfMessage((Shelf) sendObject));
            }
            //TODO rivedere questo
            /*case TEXT ->{
                destinationClient.sendMessage(new ShowText((String) sendObject));
            }*/
        }

    }

    /**
     * Sends a message in broadcast to all clients
     * @param message that is sent in broadcast
     */
    public void writeBroadcast(Message message){
        for(SocketServer server : socketMap.values()){
            server.sendMessage(message);
        }
    }

    /**
     * To return the list of usernames
     * @return
     */
    public ArrayList<String> getUsernames(){
        return new ArrayList<>(socketMap.keySet());
    }

    private Integer askCol(SocketServer destinationClient){
        // TODO controllare che la colonna sia valida
        destinationClient.sendMessage(new AskColumnSelectMsg());
        Message received = destinationClient.readMessage();
        return ((SelectColumnMessage)received).getCol();
    }

    private ArrayList<Coords> askCoords(SocketServer destinationClient){
        //TODO mettere il controllo che le coordinate siano valide client-side
        destinationClient.sendMessage(new AskTileSelectMsg());
        Message received = destinationClient.readMessage();
        return ((SelectTileMessage)received).getCoordinates();
    }

}
