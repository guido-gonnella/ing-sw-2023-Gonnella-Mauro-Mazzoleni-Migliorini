package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

import java.util.Map;

/**
 * Message from server to client<br>
 * It contains the maps with the player end points and the common objective completion
 *
 * @author Guido Gonnella
 */
public class EndStatsMessage extends Message {
    private final Map<String, Integer> player_points;
    private final Map<String, boolean[]> player_ComObj;

    public EndStatsMessage(Map<String, Integer> player_points, Map<String, boolean[]> player_ComObj) {
        super(MsgType.END_STATS);
        this.player_points = player_points;
        this.player_ComObj = player_ComObj;
    }

    public Map<String, Integer> getPlayer_points() {
        return player_points;
    }

    public Map<String, boolean[]> getPlayer_ComObj() {
        return player_ComObj;
    }
}
