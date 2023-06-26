package it.polimi.ingsw.Enumeration;

/**
 * Enumeration of all the messages' types
 *
 * @author Samuele Mazzoleni
 */
public enum MsgType {
    //Client to Server
    SELECT_TILE,
    SELECT_COL,
    SEND_NICKNAME,
    END_SEL_TILES,
    END_PL_TURN,
    NUMBER_PLAYER_REPLY,
    FULL_TILE_SELECTION,
    PUBLIC_OBJECTIVE,
    PRIVATE_OBJECTIVE,


    //Server to Client
    SELECT_TILE_REQUEST,
    SELECT_COL_REQUEST,
    FULL_SELECTION_REQUEST,
    NUMBER_PLAYER_REQUEST,
    ASK_NICKNAME,
    BOARD_UPDATE,
    SHELF_UPDATE,
    END_STATS,
    ERROR,
    DISCONNECTION,
    TEXT,

    END_GAME,
    PING
}
