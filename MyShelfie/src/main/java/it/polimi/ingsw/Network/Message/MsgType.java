package it.polimi.ingsw.Network.Message;

/**
 * Enumeration of all the messages' types
 *
 * @author Samuele Mazzoleni
 */
public enum MsgType {
    SELECT_TILE,
    SELECT_COL,
    END_SEL_TILES,
    HAND_TILE_SWAP,
    END_PL_TURN,
    BOARD_UPDATE,
    SHELF_UPDATE,
    PLAYER_UPDATE,
    END_STATS
}
