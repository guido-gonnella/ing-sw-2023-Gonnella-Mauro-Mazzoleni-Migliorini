package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.Observer.Observer;

/**
 * Controller class for the client
 * @author Guido Gonnella
 */
public class ClientController implements Observer {
    SocketClient client;



    /**
     * Method that whenever the client recieves a message, notify this instance to be updated with a message.
     * @param msg
     */
    @Override
    public void update(Message msg) {
        switch (msg.getMsgType()){
            //case END_STATS ->
            //case BOARD_UPDATE ->
            //case SHELF_UPDATE ->
            //case PLAYER_UPDATE ->
            //case HAND_UPDATE ->
        }
    }
}
