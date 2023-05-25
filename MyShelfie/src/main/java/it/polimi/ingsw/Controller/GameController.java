package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.Enumeration.GameState;
import it.polimi.ingsw.Model.Enumeration.Phase;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.Message.C2S.NumberOfPlayerMessage;
import it.polimi.ingsw.Network.Message.C2S.SelectColumnMessage;
import it.polimi.ingsw.Network.Message.C2S.SelectTileMessage;
import it.polimi.ingsw.Network.ServerPack.VirtualView;
import it.polimi.ingsw.Observer.Observer;

/**
 * Controller class for the game<br>
 * It is used server-side.
 * @author Guido Gonnella
 */
public class GameController implements Observer, Runnable {
    private Game game;
    private GameState gameState;
    private TurnController turnController;
    private VirtualView view;

    //private Map<String, ClientHandler>

    public GameController(VirtualView virtualView) {
        view = virtualView;
        this.game = new Game();

        setGameState(GameState.LOGIN);
    }


    /**
     * Method that take a message, and decides what to do depending on the state of the game
     *
     * @param msg
     * @author Guido Gonnella
     */
    public void onMessageReceived(Message msg) {

        switch (gameState) {
            case LOGIN :
                onLogin(msg);
                break;
            case INIT :
                //maybe it's useless
                break;
            case IN_GAME :
                if(msg.getUsername().equals(turnController.getCurrPlayer())) {
                    inGameState(msg);
                }
                break;
            case END:
                if(msg.getUsername().equals(turnController.getCurrPlayer())){
                    onEnd(msg);
                }
                break;
        }

    }

    private void onLogin(Message msg) {
        switch (msg.getMsgType()) {
            case NUMBER_PLAYER_REPLY -> {
                if (((NumberOfPlayerMessage) msg).getNum() >= 2 && ((NumberOfPlayerMessage) msg).getNum() <= 4) {
                    game.setMaxNumPlayer(((NumberOfPlayerMessage) msg).getNum());
                } else {
                    // send the client an error, and tell them to re-enter the number
                }
            }
        }
    }

    /**
     * Does something on the received message type
     *
     * @param msg
     */
    private void inGameState(Message msg) {
        switch (msg.getMsgType()) {
            case SELECT_TILE -> {
                turnController.setCurrPlayer(msg.getUsername());
                turnController.setPhase(Phase.PICK_TILES);
                game.selectTiles(((SelectTileMessage) msg).getX(), ((SelectTileMessage) msg).getY());

                //send to client the updated list and board
            }
            case END_SEL_TILES -> {
                turnController.setPhase(Phase.SELECT_ORDER);
                game.fillTilesInHand();
            }
            case SELECT_COL -> {
                turnController.setPhase(Phase.PICK_COLUMN);

                Player player = game.getPlayerByNick(turnController.getCurrPlayer());

                //it put the tiles in the shelf only there are enough spaces in the chosen column
                if (game.getTilesInCurrPlayerHand().size() <= player.getFreeSpaceInCol(((SelectColumnMessage) msg).getCol())) {
                    for (Tile t : game.getTilesInCurrPlayerHand()) {
                        player.placeTile(t, ((SelectColumnMessage) msg).getCol());
                    }
                }

                //send to client the updated shelf

            }
            case END_PL_TURN -> {
                if(game.getPlayerByNick(msg.getUsername()).getShelf().isFull()){
                    //the game enters the end state
                    gameState = GameState.END;
                }

                turnController.nextTurn();
                turnController.nextPlayer();
            }
        }

    }

    private void onEnd(Message msg){
        if(!turnController.sameTurn()){
            //not the same turn, some players have to finish the turn
            //it redirects tp the inGameState method
            inGameState(msg);
        }else{

        }
    }

    @Override
    public void update(Message msg) {
        switch (msg.getMsgType()) {
            case SELECT_TILE -> {
                //game.selectTiles(((SelectTileMessage) msg).getX(), ((SelectTileMessage) msg).getY());
            }
            case END_SEL_TILES -> {
                if (game.validSelection()) game.fillTilesInHand();
            }
            case SELECT_COL -> {
                for (Tile t : game.getTilesInCurrPlayerHand()) {
                    //game.getPlayerByNick(((SelectColumnMessage) msg).getUsername()).placeTile(t, ((SelectColumnMessage) msg).getCol());
                }
            }
            case END_PL_TURN -> {
                // DA capire cosa scrivere
            }
        }
    }

    private void setGameState(GameState state) {
        this.gameState = state;
    }

    public GameState getGameState(){ return this.gameState; }

    public void loginHandler(String nick) {
        if (game.getPlayerList().size() == 1) {
            //first player to be added
            game.addPlayer(nick);

            //send the request to the client for the number of player
        } else if (game.getPlayerList().size() < game.getMaxNumPlayer()) {
            game.addPlayer(nick);
            //server login accepted response
            //client handler .sendMessage(...);
        } else {
            //server sends to the client error message for the connection
            //clientHandler.sendMessage(...);
        }
    }

    public void askTileSelect() {
        //clientHandler.sendMessage(new AskSelectTileMsg)
    }

    public void askTileSwap() {
        //clientHandler.sendMessage(new AskSwapMsg);
    }

    public void askSelectCol() {
        //clientHandler.sendMessage(new AskColMsg);
    }

    public void askMaxPlayer() {
        //clientHandler.sendMessage(new MaxNumPlayerRequest);
    }


    @Override
    public void run() {

    }
}