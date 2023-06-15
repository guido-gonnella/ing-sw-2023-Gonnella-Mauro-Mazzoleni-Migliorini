package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.ClientPack.ClientSocket;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NetworkHandler implements Observer, ViewObserver, Runnable{

    private ArrayList<Coords> tempTiles;
    private ArrayList<Tile> hand;
    private final View view;
    private int column;
    private Shelf shelf;
    private int loop;
    private Board board;

    public ClientSocket client;
    private String nick;

    public NetworkHandler(View view){
            this.view = view;
            tempTiles = new ArrayList<Coords>();
            hand = new ArrayList<Tile>();
            shelf = new Shelf();
        }
    @Override
    public void onConnection(String serverAddr, int port) {
            client = new ClientSocket(serverAddr, port);
        }

    /**
    * Method that whenever the client recieves a message, notify this instance to be updated with a message.
    */
    @Override
    public void run() {
            Message msg;
            view.init();
            while(!Thread.currentThread().isInterrupted()) {
                msg = client.readMessage();

                switch (msg.getMsgType()) {
                    case ASK_NICKNAME: //ritorna un UpdatePlInfoMessage con lo username
                        view.askNickname();
                        break;
                    case NUMBER_PLAYER_REQUEST: //ritorna NumberOfPlayerMessage con un numero tra 2 e 4
                        view.askPlayerNumber();
                        break;
                    case TEXT: //stampa il testo ricevuto, non ritorna niente
                        view.showText(((TextMessage) msg).getText());
                        break;
                    case PUBLIC_OBJECTIVE: //stampa gli obiettivi pubblici, non ritorna niente
                        view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[0]);
                        view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[1]);
                        break;
                    case PRIVATE_OBJECTIVE: //stampa l'obiettivo privato, non ritorna niente
                        view.showPrivateObjective(((PrivateObjectiveMessage) msg).getPrivateObjective());
                        break;
                    case BOARD_UPDATE: //stampa la board, non ritorna niente
                        board = ((UpdateBoardMessage) msg).getBoard();
                        view.boardShow(board.getGrid());
                        break;
                    case SHELF_UPDATE: //stampa la shelf, non ritorna niente
                        shelf = ((UpdateShelfMessage) msg).getShelf();
                        view.shelfShow(shelf.getShelf());
                        break;
                    case FULL_SELECTION_REQUEST:
                        //stampa la board, restituisce un FullTileSelectionMessage con
                        //l'arraylist di coordinate selezionate e la colonna selezionata
                        //il messaggio arrivato ha già la board e la shelf

                        // quello da fare sulla view
                        selectTileRequest();
                        break;
                    case END_STATS:
                        //dice al client che la partita è finita e si è disconnesso, per la visualizzazione
                        //dei punti se ne occupa il gameController mandando dei messaggi di testo con i
                        //punteggi e il vincitore, non restituisce niente

                        view.showPoints(((EndStatsMessage) msg).getPlayer_points(), ((EndStatsMessage) msg).getPlayer_ComObj());
                        break;
                    case ERROR:
                        //it.polimi.ingsw.view.showError();
                        break;
                    default: view.showText("something went very wrong");
                }
            }
        }

    private void selectTileRequest(){
            boolean valid;

            do {
                for (loop = 0; loop < 3; loop++) {
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

    @Override
    public void onSelectCol(int col) {
            this.column = col;
        }

    /**
         * Sends to the server the chosen number of player
         * @param numPlayers the max number of players allowed in the game
         */
    @Override
    public void onPlayerNumberReply(int numPlayers){
            client.sendMessage(new NumberOfPlayerMessage(numPlayers));
        }

    @Override
    public void onSelection(ArrayList<Coords> coords, int col) {
        client.sendMessage(new FullTileSelectionMessage(coords, col));
    }

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
     * @return return true if the tiles selected is valid, in line and with at least a side free
     * The method return a boolean value, based on the coordinates of the selected tiles from the game Board.<br>
     * It checks firstly if the number of the selected tiles is not greater than three, than based on the number (one, two or three).<br><br>
     * If the tiles are only one, it checks if the tile have at least one free side. <br>
     * If the tiles are two, it checks if the x coordinates or the y coordinates are the same, then it sorts the array containing the coordinates
     * (sorting on the y if the selection is horizontal, or on the x if the selection is vertical), then checks if the absolute value of the difference of the y values
     * (x values) is 1, meaning the tiles are one next to the other, then checks if the selected tiles have one free side.<br>
     * For the selection of three elements, it first checks if the x values or the y values are the same (same row or column), then order the arraylilst by the x or the y values,
     * then checks if the difference between the coordinates (x or y values) are -1, meaning the values (x or y) are consecutive, then it checks if the coordinates
     * have at least on free side.
     * @return return true if the selection is valid, false otherwise
     * @author Guido Gonnella
     */
    public boolean validSelection(){
        ArrayList<Coords> tempCoords = new ArrayList<>(this.tempTiles);

        if(tempCoords.size() > 0 && tempCoords.size() <= 3){
            if(tempCoords.size() == 1){
                return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL);
            } else if (tempCoords.size() == 2) {
                if(tempCoords.get(0).ROW == tempCoords.get(1).ROW){
                    //swap for sorting the two element array
                    if(tempCoords.get(0).COL > tempCoords.get(1).COL){
                        Coords t = tempCoords.get(1);
                        tempCoords.set(1, tempCoords.get(0));
                        tempCoords.set(0, t);
                    }

                    if(Math.abs(tempCoords.get(0).COL - tempCoords.get(1).COL) == 1){
                        return adjacent(tempCoords.get(0).ROW, tempCoords.get(0).COL) && adjacent(tempCoords.get(1).ROW, tempCoords.get(1).COL);
                    }
                } else if (tempCoords.get(0).COL == tempCoords.get(1).COL) {
                    //swap for sorting the two element array
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
