package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller class for the client
 * @author Guido Gonnella
 */
public class ClientController implements Observer, ViewObserver {

    View view;
    Optional<Tile>[][] shelf;
    Space[][] board;
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
            case BOARD_UPDATE -> view.boardshow(board);
            case SHELF_UPDATE -> view.shelfshow(shelf);
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
