package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.coord;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;

import java.io.IOException;

/**
 * Controller class for the client
 * @author Guido Gonnella
 */
public class ClientController implements Observer, ViewObserver {

    //View view;
    private SocketClient client;
    private String nick;

    @Override
    public void onConnection(String serverAddr, int port) {
        try {
            client = new SocketClient(serverAddr, port);
            client.addObserver(this);
        } catch (IOException e) {
            // show on screen an error
        }
    }

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

    @Override
    public void onSelectTile(int x, int y) {
        client.sendData(new SelectTileMessage(nick, x, y));
    }

    @Override
    public void onSelectCol(int col) {
        client.sendData(new SelectColumnMessage(nick, col));
    }

    @Override
    public void onSwap(int to, int from) {
        client.sendData(new SwapTileInHandMessage(nick, to, from));
    }

    @Override
    public void onEndSelection() {
        client.sendData(new EndSelectTilesMessage(nick));
    }

    @Override
    public void onEndTurn() {
        client.sendData(new EndPlTurnMessage(nick));
    }
}
