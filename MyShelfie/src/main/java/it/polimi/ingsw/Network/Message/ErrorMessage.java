package it.polimi.ingsw.Network.Message;

/**
 * Error message
 */
public class ErrorMessage extends Message{
    private final String error;
    public ErrorMessage(String u, String error) {
        super(MsgType.ERROR, u);
        this.error = error;
    }

    //getter
    public String getError() {
        return error;
    }
}
