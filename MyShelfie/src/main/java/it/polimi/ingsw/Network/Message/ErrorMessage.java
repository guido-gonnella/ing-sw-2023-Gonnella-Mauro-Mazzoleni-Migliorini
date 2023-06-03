package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Error message
 */
public class ErrorMessage extends Message{
    private final String error;
    public ErrorMessage(String error) {
        super(MsgType.ERROR);
        this.error = error;
    }

    //getter
    public String getError() {
        return error;
    }
}
