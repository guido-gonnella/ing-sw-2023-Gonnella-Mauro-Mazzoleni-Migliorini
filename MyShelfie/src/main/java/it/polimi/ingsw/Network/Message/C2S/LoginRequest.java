package it.polimi.ingsw.Network.Message.C2S;
import it.polimi.ingsw.Network.Message.*;

public class LoginRequest extends Message{
    public LoginRequest() {
        super(MsgType.LOGIN_REQUEST);
    }
}
