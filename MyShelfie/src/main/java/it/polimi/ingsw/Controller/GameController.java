package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.Enumeration.GameState;
import it.polimi.ingsw.Model.Enumeration.Phase;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.Message.C2S.SelectColumnMessage;
import it.polimi.ingsw.Network.Message.C2S.SelectTileMessage;
import it.polimi.ingsw.Network.Message.C2S.SwapTileInHandMessage;
import it.polimi.ingsw.Observer.Observer;

/**
 * Controller class for the game<br>
 * It is used server-side.
 * @author Guido Gonnella
 */
public class GameController implements Observer {
    private Game game;
    private GameState gameState;
    private TurnController turnController;

    /**
     * Method that take a message, and decides what to do depending on the state of the game
     * @param msg
     * @author Guido Gonnella
     */
    public void onmessageRecieved(Message msg){
        switch (gameState){
            case INIT -> {

            }
            case GOING -> {

            }
        }
    }

    /**
     * Does something on the recieved message type
     * @param msg
     */
    public void goingState(Message msg){
        switch (msg.getMsgType()){
            case SELECT_TILE -> {
                turnController.setCurrPlayer(msg.getUsername());
                turnController.setPhase(Phase.PICK_TILES);
                game.selectTiles(((SelectTileMessage) msg).getX(), ((SelectTileMessage) msg).getY());
            }
            case END_SEL_TILES ->{
                if(turnController.getCurrPlayer().equals(msg.getUsername())){
                    turnController.setPhase(Phase.SELECT_ORDER);
                    game.fillTilesInHand();
                }else{
                    //error
                }
            }
            case SELECT_COL -> {
                if(turnController.getCurrPlayer().equals(msg.getUsername())){
                    turnController.setPhase(Phase.PICK_COLUMN);

                    Player player = game.getPlayerByNick(turnController.getCurrPlayer());

                    //it put the tiles in the shelf only there are enough spaces in the chosen column
                    if(game.getTilesInCurrPlayerHand().size() <= player.getFreeSpaceInCol(((SelectColumnMessage) msg).getCol())) {
                        for (Tile t : game.getTilesInCurrPlayerHand()) {
                            player.placeTile(t, ((SelectColumnMessage) msg).getCol());
                        }
                    }
                }else{
                    //error
                }
            }
            case HAND_TILE_SWAP -> {
                if(turnController.getCurrPlayer().equals(msg.getUsername())){
                    turnController.setPhase(Phase.SELECT_ORDER);
                    game.swapInHand(((SwapTileInHandMessage) msg).getTo(), ((SwapTileInHandMessage) msg).getFrom());
                }else{
                    //error
                }
            }
            case END_PL_TURN -> {

            }
        }

    }

    @Override
    public void update(Message msg) {
        switch (msg.getMsgType()){
            case SELECT_TILE -> {
                game.selectTiles(((SelectTileMessage) msg).getX(), ((SelectTileMessage) msg).getY());
            }
            case END_SEL_TILES -> {
                if (game.validSelection()) game.fillTilesInHand();
            }
            case SELECT_COL -> {
                for(Tile t : game.getTilesInCurrPlayerHand()){
                    game.getPlayerByNick(((SelectColumnMessage) msg).getUsername()).placeTile(t, ((SelectColumnMessage)msg).getCol());
                }
            }
            case END_PL_TURN -> {
                // DA capire cosa scrivere
            }
        }
    }
}