package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.ClientPack.ClientConnection;
import it.polimi.ingsw.Network.ClientPack.ClientSocket;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.ErrorMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.*;
import it.polimi.ingsw.Network.RMI.ClientRmi;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.View;

import java.util.ArrayList;
import java.util.Objects;

public class NetworkHandler implements Observer, ViewObserver, Runnable{

    private final ArrayList<Coords> tempTiles;
    private final ArrayList<Tile> hand;
    private final View view;
    private int column;
    private Shelf shelf;
    private int loop;
    private Board board;
    public ClientConnection client;
    private String nick;
    private final boolean socketConnection;

    public NetworkHandler(View view, boolean socketConnection) {
        this.view = view;
        tempTiles = new ArrayList<Coords>();
        hand = new ArrayList<Tile>();
        shelf = new Shelf();
        this.socketConnection = socketConnection;
    }

    /**
     * It creates the connection to the server and distinguishes between
     * the socket and rmi connection
     * @param serverAddr address of the server
     * @param port of connection
     */
    @Override
    public void onConnection(String serverAddr, int port) {
        if(socketConnection) client = new ClientSocket(serverAddr, port);
        else client = new ClientRmi(serverAddr);
    }

    /**
    * Method that whenever the client receives a message, notify this instance to be updated with a message.
    */
    @Override
    public void run() {
        Message msg;
        view.init();
        while(!Thread.currentThread().isInterrupted()) {
            msg = client.readMessage();

            if(msg == null){
                System.out.print("An error occurred\n");
                client.disconnect();
                Thread.currentThread().interrupt();
                continue;
            }

            switch ((msg).getMsgType()) {
                case ASK_NICKNAME -> //return an UpdatePlInfoMessage with the username
                        view.askNickname();
                case NUMBER_PLAYER_REQUEST -> //ritorna NumberOfPlayerMessage con un numero tra 2 e 4
                        view.askPlayerNumber();
                case TEXT -> //stampa il testo ricevuto, non ritorna niente
                        view.showText(((TextMessage) msg).getText());
                case PUBLIC_OBJECTIVE -> { //stampa gli obiettivi pubblici, non ritorna niente
                    view.showText("\n------------- PUBLIC OBJECTIVES -------------\n\n");
                    view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[0]);
                    view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[1]);
                }
                case PRIVATE_OBJECTIVE -> //stampa l'obiettivo privato, non ritorna niente
                        view.showPrivateObjective(((PrivateObjectiveMessage) msg).getPrivateObjective());
                case BOARD_UPDATE -> { //stampa la board, non ritorna niente
                    board = ((UpdateBoardMessage) msg).getBoard();
                    view.boardShow(board.getGrid());
                }
                case SHELF_UPDATE -> { //stampa la shelf, non ritorna niente
                    shelf = ((UpdateShelfMessage) msg).getShelf();
                    view.shelfShow(shelf.getShelf());
                }
                case FULL_SELECTION_REQUEST ->
                    //stampa la board, restituisce un FullTileSelectionMessage con
                    //l'arraylist di coordinate selezionate e la colonna selezionata
                    //il messaggio arrivato ha già la board e la shelf

                    // quello da fare sulla view
                        selectTileRequest();
                case END_STATS -> {
                    //dice al client che la partita è finita e si è disconnesso, per la visualizzazione
                    //dei punti se ne occupa il gameController mandando dei messaggi di testo con i
                    //punteggi e il vincitore, non restituisce niente
                    view.showText("\n================= ENDING STATS =================\n");
                    view.showPoints(((EndStatsMessage) msg).getPlayer_points(), ((EndStatsMessage) msg).getPlayer_ComObj());
                }
                case END_GAME -> {
                    Thread.currentThread().interrupt();
                    client.disconnect();
                }
                case ERROR -> {
                    //it.polimi.ingsw.view.showError();
                    view.showText(((ErrorMessage) msg).getError());
                    client.disconnect();
                    Thread.currentThread().interrupt();
                }
                default -> view.showText("something went very wrong");
            }
        }
    }


    /**
     * Method that ask the player via view methods to select the tiles from the board and then to select the column where to put the selected tiles.<br>
     * Then the method sends a {@link FullTileSelectionMessage} to the server containing the selected tiles and the column.
     */
    private void selectTileRequest() {
        boolean valid;
        int max=0;
        int tilesLeft;
        for (int i=0;i<5;i++){
            tilesLeft=shelf.tilesLeftColumn(i);
            if(max<tilesLeft) {
                max=tilesLeft;
            }
        }
        do {
            loop=0;
            if (max<3){
                loop=3-max;
            }
            for (; loop < 3; loop++) {
                view.askSelectTile();
            }
            valid = validSelection();
            if (!valid) {
                tempTiles.clear();
                view.invalidCombo();
            }
        } while (!valid);

        for (Coords tempTile : tempTiles) {
            Tile tile = board.takeTiles(tempTile.ROW, tempTile.COL).get();
            hand.add(tile);
        }
        view.showTilesInHand(hand);

        do {
            valid = true;
            view.askInsertCol();
            if (shelf.tilesLeftColumn(column) < hand.size()) {
                view.invalidColumn(column);
                valid = false;
            }
        } while (!valid);

        client.sendMessage(new FullTileSelectionMessage(tempTiles, column));

        for (Tile tile : hand) {
            shelf.putTile(tile, column);
        }

        hand.clear();
        tempTiles.clear();
    }

    /**
     * Method that add the coordinates of the selected tile to the tempTiles list, but firstly, check if the coordinates inserted are -1,-1, which means
     * the player wants to end their selection, or check if the selected coordinated are out of bounds or the space on the board is empty.
     * @param ROW x coordinate
     * @param COL y coordinate
     */
    @Override
    public void onSelectTile(int ROW, int COL) {
        if (ROW == -1 && COL == -1) {
            loop = 2;
        }
        else if (ROW>8 || ROW<0 || COL<0 || COL>8 || board.getGrid()[ROW][COL].getTile().isEmpty()) {
            view.invalidTile(ROW, COL);
            view.askSelectTile();
        }
        else {
            tempTiles.add(new Coords(ROW, COL));
        }
    }

    /**
     * Set the column attribute at the value of the parameter passed.
     * @param col - the selected column
     */
    @Override
    public void onSelectCol(int col) {
        this.column = col;
    }

    /**
         * Sends to the server the chosen number of players
         * @param numPlayers the max number of players allowed in the game
         */
    @Override
    public void onPlayerNumberReply(int numPlayers){
            client.sendMessage(new NumberOfPlayerMessage(numPlayers));
        }

    /**
     * Sends to the server the selected tiles and column, in the form of a {@link FullTileSelectionMessage}
     * @param coords the coordinates of the selected tiles
     * @param col the selected column
     */
    @Override
    public void onSelection(ArrayList<Coords> coords, int col) {
        client.sendMessage(new FullTileSelectionMessage(coords, col));
    }

    /**
     * Update the nickname with the passed parameter, and send it to the server in the form of a {@link UpdatePlInfoMessage}.
     * @param nick the new nickname
     */
    @Override
    public void onNicknameUpdate (String nick){
        this.nick = nick;
        client.sendMessage(new UpdatePlInfoMessage(nick));
    }

    /**
    * This method validates the given IPv4
    * @param ip address
    */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
         * Check if a port is valid
         * @param portStr
         * @return
         */
    public static boolean isValidPort(int portStr) {
        try {
            return portStr >= 1 && portStr <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *
     * @return return true if the tiles selected are valid, in line and with at least a side free
     * The method returns a boolean value, based on the coordinates of the selected tiles from the game Board.<br>
     * It checks firstly if the number of the selected tiles is not greater than three, than based on the number (one, two or three).<br><br>
     * If the tiles are only one, it checks if the tile has at least one free side. <br>
     * If the tiles are two, it checks if the x coordinates or the y coordinates are the same, then it sorts the array containing the coordinates
     * (sorting on the y if the selection is horizontal, or on the x if the selection is vertical), then checks if the absolute value of the difference of the y values
     * (x values) is 1, meaning the tiles are one next to the other, then checks if the selected tiles have one free side.<br>
     * For the selection of three elements, it first checks if the x values or the y values are the same (same row or column), then order the arraylilst by the x or the y values,
     * then checks if the difference between the coordinates (x or y values) are -1, meaning the values (x or y) are consecutive, then it checks if the coordinates
     * have at least on free side.
     * @return return true if the selection is valid, false otherwise
     */
    public boolean validSelection(){
        ArrayList<Coords> tempCoords = new ArrayList<>(this.tempTiles);

        if(tempCoords.size() > 0 && tempCoords.size() <= 3){
            if(tempCoords.size() == 1){
                return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL);
            } else if (tempCoords.size() == 2) {
                if(tempCoords.get(0).ROW == tempCoords.get(1).ROW){
                    //swap for sorting the two element arrays
                    if(tempCoords.get(0).COL > tempCoords.get(1).COL){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }

                    if(Math.abs(tempCoords.get(0).COL - tempCoords.get(1).COL) == 1){
                        return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL) && adjacent(tempCoords.get(1).ROW, tempCoords.get(1).COL);
                    }
                } else if (tempCoords.get(0).COL == tempCoords.get(1).COL) {
                    //swap for sorting the two element arrays
                    if(tempCoords.get(0).COL > tempCoords.get(1).COL){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }

                    if(Math.abs(tempCoords.get(0).ROW - tempCoords.get(1).ROW) == 1){
                        return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL) && adjacent(tempCoords.get(1).ROW, tempCoords.get(1).COL);
                    }
                }
            } else {
                if(tempCoords.get(0).ROW == tempCoords.get(1).ROW && tempCoords.get(1).ROW == tempCoords.get(2).ROW) {
                    //sorting 3 element array
                    if(tempCoords.get(0).COL > tempCoords.get(1).COL){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }
                    if(tempCoords.get(1).COL > tempCoords.get(2).COL){
                        Coords t = tempCoords.get(2);
                        tempCoords.set(2, tempCoords.get(1));
                        tempCoords.set(1, t);
                    }
                    if(tempCoords.get(0).COL > tempCoords.get(1).COL){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }

                    if((tempCoords.get(0).COL - tempCoords.get(1).COL == -1 && tempCoords.get(1).COL - tempCoords.get(2).COL == -1 )||
                            (tempCoords.get(0).COL - tempCoords.get(1).COL == 1 && tempCoords.get(1).COL - tempCoords.get(2).COL == 1 )){
                        return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL) && adjacent(tempCoords.get(1).ROW, tempCoords.get(1).COL) && adjacent(tempCoords.get(2).ROW, tempCoords.get(2).COL);
                    }

                }else if(tempCoords.get(0).COL == tempCoords.get(1).COL && tempCoords.get(1).COL == tempCoords.get(2).COL){
                    //sorting 3 element array
                    if(tempCoords.get(0).ROW > tempCoords.get(1).ROW){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }
                    if(tempCoords.get(1).ROW > tempCoords.get(2).ROW){
                        Coords t = tempCoords.get(2);
                        tempCoords.set(2, tempCoords.get(1));
                        tempCoords.set(1, t);
                    }
                    if(tempCoords.get(0).ROW > tempCoords.get(1).ROW){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }

                    if((tempCoords.get(0).ROW - tempCoords.get(1).ROW == -1 && tempCoords.get(1).ROW - tempCoords.get(2).ROW == -1 )||
                            (tempCoords.get(0).ROW - tempCoords.get(1).ROW == 1 && tempCoords.get(1).ROW - tempCoords.get(2).ROW == 1 )){
                        return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL) && adjacent(tempCoords.get(1).ROW, tempCoords.get(1).COL) && adjacent(tempCoords.get(2).ROW, tempCoords.get(2).COL);
                    }

                }
            }
        }

        return false;
    }

    /**
     * Checks if the tile at the passed coordinate have, at least, a free side.
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the tile has at least one free side, false otherwise
     */
    private boolean adjacent(int x, int y){
        if(x==0 || x== 8 || y==0 || y==8)
            return true;
        if(board.getGrid()[x-1][y].getTile().isEmpty())
            return true;
        if(board.getGrid()[x+1][y].getTile().isEmpty())
            return true;
        if(board.getGrid()[x][y-1].getTile().isEmpty())
            return true;
        if(board.getGrid()[x][y+1].getTile().isEmpty())
            return true;
        return false;
    }
}
