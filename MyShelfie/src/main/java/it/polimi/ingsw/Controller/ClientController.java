package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.EndStatsMessage;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.Cli;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller class for the client
 * @author Guido Gonnella
 */
public class ClientController implements Observer, ViewObserver {

    private View view;
    Optional<Tile>[][] shelf;
    Space[][] board;
    private SocketClient client;
    private String nick;

    public ClientController(View view){
        this.view = view;

    }
    @Override
    public void onConnection(String serverAddr, int port) {
        try {
            client = new SocketClient(serverAddr, port);
            client.addObserver(this);
        } catch (IOException e) {
            System.err.println("Error while connecting");
            System.exit(1);
        }
    }

    /**
     * Method that whenever the client recieves a message, notify this instance to be updated with a message.
     * @param msg
     */
    @Override
    public void update(Message msg) {
        switch (msg.getMsgType()){
            case BOARD_UPDATE :
                view.boardshow(board);
                break;
            case SHELF_UPDATE :
                view.shelfshow(shelf);
                break;
            case NUMBER_PLAYER_REQUEST:
                view.askplayernumber();
                break;
            case SELECT_TILE_REQUEST:
                view.askselecttile();
                break;
            case HAND_TILE_SWAP_REQUEST:
                view.askswap(0);
                break;
            case SELECT_COL_REQUEST:
                view.askinsertcol();
                break;
            case ERROR:
                //view.showError();
                break;
            case END_STATS:
                //view.showpoints(); <- dove si trova la map?
                //view.showpoints(((EndStatsMessage) msg).getPlayer_points().get(nick)); <- ???
                break;
            case LOGIN_REPLY:
                //view.showLoginReply();
                break;
            /*case END_TURN_REQUEST:
                view.updateShelf(Shelf);
                onEndTurn();
                break;*/
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
    public void onSwap(ArrayList<Integer> hand) {
        client.sendData(new SwapTileInHandMessage(nick, 0,0));
    }

    /**
     * Sends to the server when they want to end the tile selection phase
     */
    @Override
    public void onEndSelection() {
        client.sendData(new EndSelectTilesMessage());
    }

    /**
     * Sends to the server when they want to end the turn
     */
    @Override
    public void onEndTurn() {
        client.sendData(new EndPlTurnMessage(nick));
    }

    /**
     * Sends to the server the chosen number of player
     * @param numPlayers the max number of players allowed in the game
     */
    @Override
    public void onPlayerNumberReply(int numPlayers){ client.sendData(new NumberOfPlayerMessage(nick, numPlayers));}

    /**
     * This method validates the given IPv4
     * @param ip address
     */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
     * Check if a port is valid
     * @param portStr
     * @return
     */
    public static boolean isValidPort(int portStr) {
        try {
            return portStr >= 1 && portStr <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
