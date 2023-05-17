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
    private Map<String, Integer> playerturn;

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

        //initializing the map with all the player nickname as the keys with the value 0
        //every new turn, the corresponding value gets increased by 1
        playerturn = new HashMap<>();
        for(String p : playerQueue){
            playerturn.put(p, 0);
        }

        game.setFirstPlayer(playerQueue.get(0));
        currPlayer = playerQueue.get(0);

        phase = Phase.PICK_TILES;
    }

    /**
     * To check if all the players are at the same turn<br>
     * It checks if the distinct values out of the stream of the values inside the hashmap playerturn, which contains the players' nicknames and
     * their turn numbers, are only one distinct value.
     * @return true if the player are at the same turn, false otherwise
     */
    public boolean sameTurn(){
        return this.playerturn.values().stream().distinct().count() == 1;
    }

    /**
     * Picks the next player from the queue
     */
    public void nextPlayer(){
        //add 1 to the turn value of the current player
        playerturn.merge(currPlayer, 1, Integer::sum);

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
