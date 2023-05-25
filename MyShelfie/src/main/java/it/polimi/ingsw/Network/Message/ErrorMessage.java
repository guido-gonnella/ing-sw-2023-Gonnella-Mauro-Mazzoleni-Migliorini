package it.polimi.ingsw.Network.Message;

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
