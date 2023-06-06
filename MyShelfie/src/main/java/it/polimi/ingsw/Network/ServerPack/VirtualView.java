package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.S2C.TextMessage;
import it.polimi.ingsw.Network.Message.S2C.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that acts like a view for the server, it handles the "unpackaging"
 * and "packaging" of the messages from and to the client
 */
public class VirtualView {

    private final Map<String, SocketServer> socketMap;
    private final Server server;

    /**
     * Constructor of the class
     */
    public VirtualView(Server server){
        this.socketMap = new HashMap<>();
        this.server = server;
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
     * Reads all the data needed from the client, the tiles selected and the shelf's column
     */
    public FullTileSelectionMessage readAll(String user, AskFullMsg msg){
        SocketServer destinationClient = socketMap.get(user);
        destinationClient.sendMessage(msg);
        Message received;

        do {
            received = destinationClient.readMessage();
            /* if(messaggio di chat) mandalo a chi deve leggerlo
            *  if(messaggio di errore) rimuovi il giocatore da cui arriva
            * */
        } while(received.getMsgType() != MsgType.FULL_TILE_SELECTION);

        return ((FullTileSelectionMessage) received);
    }

    /**
     * Sends an output to the client, the type of the message and its content
     * have to be already defined in the gameController
     * @param user that will read the output
     * @param message that the client will receive
     */
    public void write(String user, MsgType message, Object sendObject){
        SocketServer destinationClient = socketMap.get(user);

        switch(message) {
            case SHELF_UPDATE -> destinationClient.sendMessage(new UpdateShelfMessage((Shelf) sendObject));
            case TEXT -> destinationClient.sendMessage(new TextMessage((String) sendObject));
            case PUBLIC_OBJECTIVE -> destinationClient.sendMessage(new PublicObjectiveMessage((PubObjType[]) sendObject));
            case PRIVATE_OBJECTIVE -> destinationClient.sendMessage(new PrivateObjectiveMessage((PrivateObjective) sendObject));
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

    public void removeUsername(String username){
        server.removeUsername(username);
    }

}
