package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Network.Message.ErrorMessage;
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

    private final Map<String, ServerConnection> connectionMap;
    private final Server server;

    /**
     * Constructor of the class
     */
    public VirtualView(Server server){
        this.connectionMap = new HashMap<>();
        this.server = server;
    }

    /**
     * Adds a client to the virtualView
     * @param username of the user
     * @param serverSocket associated with the user
     */
    public void addClient(String username, ServerConnection serverSocket){
        connectionMap.put(username, serverSocket);
    }

    /**
     * Reads all the data needed from the client; the tiles selected and the shelf's column
     */
    public FullTileSelectionMessage readAll(String user, AskFullMsg msg){
        ServerConnection destinationClient = connectionMap.get(user);
        destinationClient.sendMessage(msg);
        Message received;

        do {
            received = destinationClient.readMessage();
        } while(received.getMsgType() != MsgType.FULL_TILE_SELECTION);

        return ((FullTileSelectionMessage)received);

    }

    /**
     * Sends an output to the client, the type of the message and its content
     * have to be already defined in the gameController
     * @param user that will read the output
     * @param message that the client will receive
     */
    public void write(String user, MsgType message, Object sendObject){
        ServerConnection destinationClient = connectionMap.get(user);

        switch(message) {
            case SHELF_UPDATE -> destinationClient.sendMessage(new UpdateShelfMessage((Shelf) sendObject));
            case TEXT -> destinationClient.sendMessage(new TextMessage((String) sendObject));
            case PUBLIC_OBJECTIVE -> destinationClient.sendMessage(new PublicObjectiveMessage((PubObjType[]) sendObject));
            case PRIVATE_OBJECTIVE -> destinationClient.sendMessage(new PrivateObjectiveMessage((PrivateObjective) sendObject));
            case BOARD_UPDATE -> destinationClient.sendMessage(new UpdateBoardMessage((Board) sendObject));
            case END_GAME -> destinationClient.sendMessage(new EndGameMessage());
        }
    }

    /**
     * Sends a message in broadcast to all clients
     * @param message that is sent in broadcast
     */
    public void writeBroadcast(MsgType message, Object sendObject){
        switch(message) {
            case SHELF_UPDATE -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new UpdateShelfMessage((Shelf) sendObject));
                }
            }
            case TEXT -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new TextMessage((String) sendObject));
                }
            }
            case PUBLIC_OBJECTIVE -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new PublicObjectiveMessage((PubObjType[]) sendObject));
                }
            }
            case PRIVATE_OBJECTIVE -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new PrivateObjectiveMessage((PrivateObjective) sendObject));
                }
            }
            case BOARD_UPDATE -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new UpdateBoardMessage((Board) sendObject));
                }
            }
            case END_GAME -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new EndGameMessage());
                }
            }
            case ERROR -> {
                for(ServerConnection server : connectionMap.values()){
                    server.sendMessage(new ErrorMessage((String) sendObject));
                }
            }
        }
    }

    /**
     * Sends to all the client connected at the game the statistics of the game just ended, in the form of a {@link EndStatsMessage}.
     * @param mapPoints a map containing the players' username as the keys, and their points as the values
     * @param mapObjective a map containing the players' username as the keys, and their completion of the public objectives in the form of an array of two boolean
     */
    public void endGame(Map<String, Integer> mapPoints, Map<String, boolean[]> mapObjective) {
        for(ServerConnection server : connectionMap.values()){
            server.sendMessage(new EndStatsMessage(mapPoints, mapObjective));
        }
    }

    /**
     * To return the list of usernames
     * @return the list containing the username of all the players connected to the game
     */
    public ArrayList<String> getUsernames(){
        return new ArrayList<>(connectionMap.keySet());
    }

    /**
     * Remove the username from the sever.
     * @param username the player's username
     */
    public void removeUsername(String username){
        server.removeUsername(username);
    }
}
