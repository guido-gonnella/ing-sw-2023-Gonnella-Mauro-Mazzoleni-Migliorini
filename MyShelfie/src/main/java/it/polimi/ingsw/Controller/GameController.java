package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.Message.C2S.SelectColumnMessage;
import it.polimi.ingsw.Network.Message.C2S.SelectTileMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Observer.Observer;

public class GameController implements Observer {
    private Game game;


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