package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Model.Shelf;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.ClientPack.ClientConnection;
import it.polimi.ingsw.Network.ClientPack.ClientSocket;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Network.Message.C2S.NumberOfPlayerMessage;
import it.polimi.ingsw.Network.Message.C2S.UpdatePlInfoMessage;
import it.polimi.ingsw.Network.Message.ErrorMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.*;
import it.polimi.ingsw.Network.RMI.ClientRmi;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.View;

import java.util.ArrayList;

public class NetworkHandler implements Observer, ViewObserver, Runnable{

    private static ArrayList<Coords> tempTiles;
    private static ArrayList<Tile> hand;
    private  final View view;
    private static int column;
    private static Shelf shelf;
    private static int loop;
    private static Board board;
    public static ClientConnection client;
    private static String nick;
    private final boolean socketConnection;
    private boolean clientlock;


    public NetworkHandler(View view, boolean socketConnection){
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

        clientlock=false;
    }

    /**
    * Method that whenever the client receives a message, notify this instance to be updated with a message.
    */
    @Override
    public void run() {
        Message msg;
        clientlock= true;
        view.init();
        while (clientlock) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        while (!Thread.currentThread().isInterrupted()) {
            msg=client.readMessage();

            if(msg == null){
                System.out.print("An error occurred\n");
                client.disconnect();
                Thread.currentThread().interrupt();
                continue;
            }

            switch (msg.getMsgType()) {
                case ASK_NICKNAME:
                    clientlock = true;
                    view.askNickname();
                    while (clientlock) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case NUMBER_PLAYER_REQUEST:
                    clientlock = true;
                    view.askPlayerNumber();
                     while (clientlock) {
                         try {
                             Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case TEXT:
                    view.showText(((TextMessage) msg).getText());
                    break;
                case PUBLIC_OBJECTIVE:
                    view.showText("\n------------- PUBLIC OBJECTIVES -------------\n\n");
                    view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[0]);
                    view.showPublicObjective(((PublicObjectiveMessage) msg).getPublicObjectives()[1]);
                    break;
                case PRIVATE_OBJECTIVE:
                    view.showPrivateObjective(((PrivateObjectiveMessage) msg).getPrivateObjective());
                    break;
                case BOARD_UPDATE:
                    board = ((UpdateBoardMessage) msg).getBoard();
                    view.boardShow(board.getGrid());
                    break;
                case SHELF_UPDATE:
                    shelf = ((UpdateShelfMessage) msg).getShelf();
                    view.shelfShow(shelf.getShelf());
                    break;
                case FULL_SELECTION_REQUEST:
                    selectTileRequest();
                    break;
                case END_STATS:
                    view.showText("\n================= ENDING STATS =================\n");
                    view.showPoints(((EndStatsMessage) msg).getPlayer_points(), ((EndStatsMessage) msg).getPlayer_ComObj());
                    break;
                case END_GAME:
                    Thread.currentThread().interrupt();
                    client.disconnect();
                    break;
                case ERROR:
                    view.showText(((ErrorMessage) msg).getError());
                    client.disconnect();
                    Thread.currentThread().interrupt();
                    break;
                default:
                    view.showText("something went very wrong");
            }
        }
    }
    /**
     * Method that ask the player via view methods to select the tiles from the board and then to select the column where to put the selected tiles.<br>
     * Then the method sends a {@link FullTileSelectionMessage} to the server containing the selected tiles and the column.
     */
    private void selectTileRequest(){
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
                clientlock=true;
               view.askSelectTile();
                while(clientlock)
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
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
            loop=0;
            valid = true;
            view.askInsertCol();
            while(loop==0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
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
            loop = 3;
            clientlock=false;
        }
        else if (ROW>8 || ROW<0 || COL<0 || COL>8 || board.getGrid()[ROW][COL].getTile().isEmpty()) {
            view.invalidTile(ROW, COL);
            loop-=1;
            clientlock=false;
        }
        else {
            clientlock=false;
            tempTiles.add(new Coords(ROW, COL));
        }
    }
    /**
     * Set the column attribute at the value of the parameter passed.
     * @param col - the selected column
     */
    @Override
    public void onSelectCol(int col) {
        column = col;
        loop=1;
    }

    /**
         * Sends to the server the chosen number of player
         * @param numPlayers the max number of players allowed in the game
         */
    @Override
    public void onPlayerNumberReply(int numPlayers){
        client.sendMessage(new NumberOfPlayerMessage(numPlayers));

        clientlock=false;
    }
    /**
     * Update the nickname with the passed parameter, and send it to the server in the form of a {@link UpdatePlInfoMessage}.
     * @param nick the new nickname
     */
    @Override
    public void onNicknameUpdate (String nick){
        NetworkHandler.nick = nick;
        client.sendMessage(new UpdatePlInfoMessage(nick));

        clientlock=false;

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
