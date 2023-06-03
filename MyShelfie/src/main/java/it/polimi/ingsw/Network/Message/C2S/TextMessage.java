package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

public class TextMessage extends Message {

    String text;
    public TextMessage(String text) {
        super(MsgType.TEXT);
        this.text = text;
    }

    public String getText(){return text;}
}
