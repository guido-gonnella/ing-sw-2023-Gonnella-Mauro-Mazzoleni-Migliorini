package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from server to the client, notifying to update its view to update the board<br>
 * It sends the information about the updated board
 * @author Guido Gonnella
 */
public class UpdateBoardMessage extends Message {
    private final Board board;

    public UpdateBoardMessage(String u, Board board) {
        super(MsgType.BOARD_UPDATE, u);
        this.board = board;
    }

    @Override
    public String toString() {
        return "UpdateBoardMessage{" +
                "username" + this.getUsername() +
                "Type"+ this.getMsgType() +
                "board=" + board +
                '}';
    }

    public Board getBoard() {
        return board;
    }
}
