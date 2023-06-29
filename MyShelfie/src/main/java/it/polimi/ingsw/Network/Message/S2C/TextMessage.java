package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Sends to the player a {@link String}
 */
public class TextMessage extends Message {
    String text;

    public TextMessage(String text) {
        super(MsgType.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
