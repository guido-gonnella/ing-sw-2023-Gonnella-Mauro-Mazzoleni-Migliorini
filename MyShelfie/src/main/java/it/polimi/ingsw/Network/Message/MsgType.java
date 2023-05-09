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
    HAND_TILE,
    END_PL_TURN,
    LOGIN_REQUEST,
    NUMBER_PLAYER_REPLY,

    //Server to Client
    SELECT_TILE_REQUEST,
    SELECT_COL_REQUEST,
    HAND_TILE_SWAP_REQUEST,
    NUMBER_PLAYER_REQUEST,
    LOGIN_REPLY,
    BOARD_UPDATE,
    SHELF_UPDATE,
    PLAYER_UPDATE,
    END_STATS,
    HAND_UPDATE,
    ERROR
}
