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
    private final int x, y;
    private final ArrayList<Coords> coords;

    public SelectTileMessage(String u, int x, int y) {
        super(MsgType.SELECT_TILE);
        this.x = x;
        this.y = y;
        coords = new ArrayList<>();
    }

    public void addCoord(int x, int y){
        coords.add(new Coords(x,y));
    }

    // GETTER
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Coords> getCoordinates(){ return coords;}
}
