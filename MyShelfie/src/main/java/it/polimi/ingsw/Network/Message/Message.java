package it.polimi.ingsw.Network.Message;

import java.io.Serializable;

/**
 * Message class
 * It has two attributes: the type of the message and the username of the player involved
 *
 * @author Samuele Mazzoleni
 */
public class Message implements Serializable {

    private MsgType type;
    private String username;

    public Message(MsgType t, String u){
        this.type = t;
        this.username = u;
    }

    /**
     * Getter of the message's type
     * @return the type of the message sent
     */
    public MsgType getMsgType() {
        return type;
    }

    /**
     * getter of the message's user
     * @return the nickname of the user
     */
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}
