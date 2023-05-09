package it.polimi.ingsw.Controller;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import it.polimi.ingsw.FA.Persistence;
import it.polimi.ingsw.Model.Enumeration.Phase;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Message.Message;

import java.io.Serializable;
import java.util.*;

/**
 * Controller for managing the turn
 * @author Guido Gonnella
 */
public class TurnController implements Serializable {
    private Game game;
    private String currPlayer;
    private List<String> playerQueue;

    private GameController gameController;
    private Phase phase;

    Persistence persistence;

    /**
     * Constructor of the turn controller<br>
     * it create a new game
     * @param gameController
     */
    public TurnController(GameController gameController) {
        this.game = new Game();
        this.gameController = gameController;

        this.playerQueue = new ArrayList<String>(game.getPlayerList());

        game.setFirstPlayer(playerQueue.get(0));
        currPlayer = playerQueue.get(0);

        phase = Phase.PICK_TILES;
    }

    /**
     * Picks the next player from the queue
     */
    public void nextPlayer(){
        phase = Phase.PICK_TILES;

        if(playerQueue.indexOf(currPlayer) + 1 < playerQueue.size()){
            currPlayer = playerQueue.get(playerQueue.indexOf(currPlayer) + 1);
        }else{
            currPlayer = playerQueue.get(0);
        }
    }

    public void nextTurn(){
        phase = Phase.PICK_TILES;

        // salvataggio dello stato del controller
        persistence = new Persistence();
        persistence.save(gameController);
    }

    /**
     * Setter for the turn phase
     * @param phase
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * Setter for the current player
     * @param nick nickname
     */
    public void setCurrPlayer(String nick){ this.currPlayer = nick; }

    /**
     * Getter for the current player
     * @return the nickname of the current player
     */
    public String getCurrPlayer() {
        return currPlayer;
    }

    /**
     * Getter for the current phase
     * @return the turn current phase
     */
    public Phase getPhase() {
        return phase;
    }

    public void broadcastMessaege(Message msg){
        //sends message for all clientHandler...
    }
}
