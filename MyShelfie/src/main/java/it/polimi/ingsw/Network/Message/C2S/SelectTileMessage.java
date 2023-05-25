package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

import java.util.ArrayList;

/**
 * Message from client to server<br>
 * It sends the server the coords of the board's tile the player has just chosen.
 * The server will remember the chosen tiles to permit the player to redo the action and to cancel a wrong tiles selection
 *
 * @author Samuele Mazzoleni
 */
//TODO gli attributi x e y possono essere rimossi
public class SelectTileMessage extends Message {
    private final ArrayList<Coords> coords;

    public SelectTileMessage(ArrayList<Coords> coords) {
        super(MsgType.SELECT_TILE);
        this.coords = new ArrayList<>(coords);
    }


    // GETTER
    public ArrayList<Coords> getCoordinates(){ return coords;}
}
