package it.polimi.ingsw.Network.Message;

/**
 * Enumeration of all the messages' types
 *
 * @author Samuele Mazzoleni
 */
public enum MsgType {
    //Client to Server
    SELECT_TILE,
    SELECT_COL,
    END_SEL_TILES,
    HAND_TILE_SWAP,
    END_PL_TURN,

    //Server to Client
    BOARD_UPDATE,
    SHELF_UPDATE,
    PLAYER_UPDATE,
    END_STATS,
    HAND_UPDATE,
    ERROR
}
