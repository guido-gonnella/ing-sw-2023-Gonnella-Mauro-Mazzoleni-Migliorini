package it.polimi.ingsw.Controller;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Message.C2S.*;
import it.polimi.ingsw.Network.ClientPack.NewClientSocket;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Network.Message.S2C.*;
import it.polimi.ingsw.Network.SocketClient;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.Cli;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class NetworkHandler implements Observer, ViewObserver {

    private ArrayList<Coords> tempTiles;
    private ArrayList<Tile> hand;
    private ArrayList <Integer> order;
    private View view;
    private int column;

         Shelf shelf;
        Board board;
        private NewClientSocket client;
        private String nick;
        private int loop;

    public NetworkHandler(View view){
            this.view = view;
            tempTiles=new ArrayList<>();
            hand=new ArrayList<>();
        }
        @Override
        public void onConnection(String serverAddr, int port) {
            try {
                client = new NewClientSocket(serverAddr, port);
            } catch (IOException e) {
                System.err.println("Error while connecting");
                System.exit(1);
            }
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
                            }
                        }
                    } while(valid);
                    for (Coords tempTile : tempTiles) {
                        hand.add((board.takeTiles(tempTile.x, tempTile.y)).get());
                    }
                    view.showtilesinhand(hand);
                    view.askswap(hand.size());
                    view.askinsertcol();
                    try {
                        client.sendMessage(new FullTileSelectionMessage(tempTiles,order,column));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }for (int i = 0; i < hand.size();i++ ){
                    try {
                        shelf.putTile(hand.get(order.get(i)),column);
                    } catch (ColumnAlreadyFullException e) {
                        throw new RuntimeException(e);
                    } catch (OutOfShelfException e) {
                        throw new RuntimeException(e);
                    }
                }
                    hand.clear();
                    view.shelfshow(shelf.getShelf());
                    onEndTurn();
                    break;
                case HAND_TILE:
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
                case LOGIN_REQUEST:
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
            try {
                client.sendMessage(new EndSelectTilesMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Sends to the server when they want to end the turn
         */
        @Override
        public void onEndTurn() {
            try {
                client.sendMessage(new EndPlTurnMessage(nick));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Sends to the server the chosen number of player
         * @param numPlayers the max number of players allowed in the game
         */
        @Override
        public void onPlayerNumberReply(int numPlayers){
            try {
                client.sendMessage(new NumberOfPlayerMessage(nick, numPlayers));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void onNicknameUpdate (String Nick){
            this.nick=Nick;
            try {
                client.sendMessage(new UpdatePlInfoMessage(MsgType.PLAYER_UPDATE, nick)); //TODO updateplinfo is this correct?
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
