package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.ClientPack.ClientSocket;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkHandler implements Observer, ViewObserver {

    private ArrayList<Coords> tempTiles;
    private ArrayList<Tile> hand;
    private ArrayList <Integer> order;
    private View view;
    private int column;

         Shelf shelf;
        Board board;
        private ClientSocket client;
        private String nick;
        private int loop;

    public NetworkHandler(View view){
            this.view = view;
            tempTiles = new ArrayList<>();
            hand = new ArrayList<>();
        }
        @Override
        public void onConnection(String serverAddr, int port) {
            client = new ClientSocket(serverAddr, port);
            update(client.readMessage());
        }

        /**
         * Method that whenever the client recieves a message, notify this instance to be updated with a message.
         * @param msg
         */
        @Override
        public void update(Message msg) {
            boolean valid;
            switch (msg.getMsgType()) {
                case ASK_NICKNAME:
                    view.asknickname();
                    break;
                case BOARD_UPDATE:
                    this.board= ((UpdateBoardMessage)msg).getBoard();
                    view.boardshow(board.getGrid());
                    break;
                case SHELF_UPDATE:
                    this.shelf= ((UpdateShelfMessage)msg).getShelf();
                    view.shelfshow(shelf.getShelf());
                    break;
                case NUMBER_PLAYER_REQUEST:
                    view.askplayernumber();
                    break;
                case SELECT_TILE_REQUEST:
                    do {
                        for (loop = 0; loop < 3; loop++) {
                            view.askselecttile();
                        }
                        valid =!validSelection();
                        if(valid){
                            {
                                tempTiles.clear();
                                view.invalidcombo();
                            }
                        }
                    } while(valid);
                    for (Coords tempTile : tempTiles) {
                        hand.add((board.takeTiles(tempTile.x, tempTile.y)).get());
                    }
                    view.showtilesinhand(hand);
                    view.askswap(hand.size());
                    view.askinsertcol();
                    client.sendMessage(new FullTileSelectionMessage(tempTiles,order,column));
                    for (int i = 0; i < hand.size();i++ ){
                        //TODO prima di fare questo controllare che la scelta di colonna sia valida
                        shelf.putTile(hand.get(order.get(i)),column);

                    }
                    hand.clear();
                    view.shelfshow(shelf.getShelf());
                    onEndTurn();
                    break;
                case PUBLIC_OBJECTIVE:
                    view.showpublicobjective(((PublicObjectiveMessage)msg).getPublicObjectives()[0]);
                    view.showpublicobjective(((PublicObjectiveMessage)msg).getPublicObjectives()[1]);
                    break;
                case PRIVATE_OBJECTIVE:
                    view.showprivateobjective(((PrivateObjectiveMessage)msg).getPrivateObjective());
                    break;
                case SELECT_COL_REQUEST:
                    view.askinsertcol();
                    break;
                case ERROR:
                    //it.polimi.ingsw.view.showError();
                    break;
                case END_STATS:
                    view.showpoints(((EndStatsMessage)msg).getPlayer_points(),((EndStatsMessage)msg).getPlayer_ComObj());
                    break;
            }
            update(client.readMessage());
        }

        @Override
        public void onSelectTile(int x, int y) {
            if (x>-1 && y>-1) {
                if(x>10||y>10) {
                    tempTiles.clear();
                    loop=-1;
                    view.invalidTile(10,10);
                }else{
                    if (!(board.getGrid()[x][y].getTile().isEmpty())) {
                        tempTiles.add(new Coords(x, y));
                    } else {
                        view.invalidTile(x, y);
                        view.askselecttile();
                    }
                }

            }else {
                loop=4;
            }

        }

        @Override
        public void onSelectCol(int col) {

                this.column=col;
        }

        @Override
        public void onSwap(ArrayList<Integer> positions) {
            this.order=positions;
        }

        /**
         * Sends to the server when they want to end the tile selection phase
         */
        @Override
        public void onEndSelection() {
            client.sendMessage(new EndSelectTilesMessage());
        }

        /**
         * Sends to the server when they want to end the turn
         */
        @Override
        public void onEndTurn() {
            //client.sendMessage(new EndPlTurnMessage(nick)); // vecchio, nick è inutile (?)
            client.sendMessage(new EndPlTurnMessage());
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
        public void onNicknameUpdate (String Nick){
            this.nick=Nick;
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
     * For the the selection of three elements, it first checks if the x values or the y values are the same (same row or column), then order the arraylilst by the x or the y values,
     * then checks if the difference between the coordinates (x or y values) are -1, meaning the values (x or y) are consecutive, then it checks if the coordinates
     * have at least on free side.
     * @return return true if the selection is valid, false otherwise
     * @author Guido Gonnella
     */
    public boolean validSelection(){
        if(tempTiles.size() > 0 && tempTiles.size() <= 3){
            if(tempTiles.size() == 1){
                return adjacent(tempTiles.get(0).x, tempTiles.get(0).y);
            } else if (tempTiles.size() == 2) {
                if(tempTiles.get(0).x == tempTiles.get(1).x){
                    //swap for sorting the two element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).y - tempTiles.get(1).y) == 1){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y);
                    }
                } else if (tempTiles.get(0).y == tempTiles.get(1).y) {
                    //swap for sorting the two element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).x - tempTiles.get(1).x) == 1){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y);
                    }
                }
            } else if (tempTiles.size() == 3) {
                if(tempTiles.get(0).x == tempTiles.get(1).x && tempTiles.get(1).x == tempTiles.get(2).x){
                    //sorting 3 element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).y > tempTiles.get(2).y){
                        Coords t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if((tempTiles.get(0).y - tempTiles.get(1).y == -1 && tempTiles.get(1).y - tempTiles.get(2).y == -1 )||
                            (tempTiles.get(0).y - tempTiles.get(1).y == 1 && tempTiles.get(1).y - tempTiles.get(2).y == 1 )){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y) && adjacent(tempTiles.get(2).x, tempTiles.get(2).y);
                    }

                }else if(tempTiles.get(0).y == tempTiles.get(1).y && tempTiles.get(1).y == tempTiles.get(2).y){
                    //sorting 3 element array
                    if(tempTiles.get(0).x > tempTiles.get(1).x){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).x > tempTiles.get(2).x){
                        Coords t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).x > tempTiles.get(1).x){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if((tempTiles.get(0).x - tempTiles.get(1).x == -1 && tempTiles.get(1).x - tempTiles.get(2).x == -1 )||
                            (tempTiles.get(0).x - tempTiles.get(1).x == 1 && tempTiles.get(1).x - tempTiles.get(2).x == 1 )){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y) && adjacent(tempTiles.get(2).x, tempTiles.get(2).y);
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
