package it.polimi.ingsw.Network.Message;

import java.io.Serializable;

/**
 * Message class
 * It has two attributes: the type of the message and the username of the player involved
 *
 * @author Samuele Mazzoleni
 */

/*TODO modificare i messaggi, non è necessario inserire come parametro il giocatore a cui è destinato
   ci pensa la virtalView (attuale PlayerController)  a mandarlo al giocatore corretto
 */
public class Message implements Serializable {

    private MsgType type;
    private String username;

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
