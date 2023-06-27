package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Shelf;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;
import javafx.geometry.Bounds;

/**
 * The server use this message to send to the client the board and the player's shelf.<br>
 * For example the sever send this message at the start and the finish of the player's turn.
 */
public class AskFullMsg extends Message {

    private final Board board;
    private final Shelf shelf;
    public AskFullMsg(Board board, Shelf shelf) {
        super(MsgType.FULL_SELECTION_REQUEST);
        this.board = board;
        this.shelf = shelf;
    }

    public Board getBoard() {
        return board;
    }

    public Shelf getShelf() {
        return shelf;
    }
}
