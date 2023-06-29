package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Enumeration.MsgType;

import java.io.Serializable;

/**
 * Message class
 * It has two attributes: the type of the message and the username of the player involved
 *
 * @author Samuele Mazzoleni
 */

public class Message implements Serializable {

    private MsgType type;

    public Message(MsgType t){
        this.type = t;
    }

    /**
     * Getter of the message's type
     * @return the type of the message sent
     */
    public MsgType getMsgType() {
        return type;
    }

    /**
     * toString method to visualize the Message in the form of a {@link String}
     * @return
     */
    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                //", username='" + username + '\'' +
                '}';
    }
}
