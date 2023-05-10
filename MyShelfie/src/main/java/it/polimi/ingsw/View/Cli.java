package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Observer.ViewObservable;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * View by commandline
 * @author Andrea Migliorini
 */
public class Cli extends ViewObservable implements View{
    private final PrintStream out;
    private Scanner input;
    private String temp;
    public Cli(){out= System.out;
        out.print("""
                 __          __  _                            _______                         _____ _          _ ______      \s
                 \\ \\        / / | |                          |__   __|                       / ____| |        | |  ____(_)    \s
                  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___     | | ___    _ __ ___  _   _  | (___ | |__   ___| | |__   _  ___\s
                   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\    | |/ _ \\  | '_ ` _ \\| | | |  \\___ \\| '_ \\ / _ \\ |  __| | |/ _ \\
                    \\  /\\  /  __/ | (_| (_) | | | | | |  __/    | | (_) | | | | | | | |_| |  ____) | | | |  __/ | |    | |  __/
                     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|    |_|\\___/  |_| |_| |_|\\__, | |_____/|_| |_|\\___|_|_|    |_|\\___|
                                                                                      __/ |                                   \s
                                                                                     |___/                                     \
                """);
    }

    /**
     * starts procedure to connect to the server, no checks on validity of ip address nor port
     * socket only for now
     * @author Andrea Migliorini
     */
    @Override
    public  void init(){
        String serverAddr = "localhost"; //default value
        int port = 8080; //default value
        out.print("Please input the ip address of the server");
        Scanner in = new Scanner(System.in);
        Boolean valid = false;
        do {
            out.print("Please input the address of the server");
            try{
                serverAddr = ReadText();
                if(serverAddr.equals("")){
                    valid = false;
                }
                else if(ClientController.isValidIpAddress(serverAddr)){
                    valid = true;
                }
                else{
                    valid = false;
                }
            }catch(InputMismatchException e){
                out.print("Please input a valid port ");
                valid = false;
            }catch(NoSuchElementException e1){
                out.print("Please input a valid port");
                valid = false;
            }
        } while (!valid);

        valid = false;

        do {
            out.print("Please input the port of the server");
            try{
                port = ReadInt();
                if(ClientController.isValidPort(port)) {
                    valid = true;
                }
                else{
                    valid = false;
                }
            }catch(InputMismatchException e){
                out.print("Please input a valid port ");
                valid = false;
            }catch(NoSuchElementException e1){
                out.print("Please input a valid port");
                valid = false;
            }
        } while (!valid);

        int finalPort = port;
        String Finalserveraddr = serverAddr;

        notifyObservers(obs -> { try {
             obs.onConnection(Finalserveraddr, finalPort);
                 } catch (IOException e) {
                    throw new RuntimeException(e);
                 }
            }
         );
    }

    @Override
    public void asknickname() {
        out.print("First Insert your Username: ");
        temp= ReadText();
        //notifyObservers(obs->obs.UpdateNickname(temp));
    }

    /**
     * asks user to select tile, no checks on board limits
     * @author Andrea Migliorini
     */
    @Override
    public void askselecttile(){
        Integer y = -1, x = -1;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("input \"x,y\" coordinates of the tile, to stop input \"-1,-1\"");
        do {
            try{valid=true;
                y =in.nextInt();
            }catch(InputMismatchException e){
                out.print("please input valid coordinates: like 1,2 or 0,3");
                valid=false;
            }catch(NoSuchElementException e1){
                out.print("please input valid coordinates: like 1,2 or 0,3");
                valid=false;
            }

            if( valid){
                try{ x =in.nextInt();}
                catch(InputMismatchException e){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }catch(NoSuchElementException e1){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }
            }
        } while (!valid);
        Integer finalX = x;
        Integer finalY = y;
        notifyObservers(obs -> obs.onSelectTile(finalX, finalY));

    }

    /**
     * asks to order the tiles to insert the colums, checks position outside of max size of hand and different position of tiles
     * @author Andrea Migliorini
     */
    @Override
    public void askswap(){
        int to=4, from=4;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("input \"x,y\" the first tile and to which position to move it in, to confirm the order of the tiles type \"-1,-1\"");
        do {
            try{valid=true;
                from =in.nextInt();}
            catch(InputMismatchException e){
                out.print("please input valid positions: like 1,2 or 0,2");
                valid=false;
            }catch(NoSuchElementException e1){
                out.print("please input valid positions: like 1,2 or 0,2");
                valid=false;
            }
            if (from >2){valid= false;
                out.print("please input valid positions: like 1,2 or 0,2");
            }
            if(valid){
                try{
                    to =in.nextInt();
                    if (to > 3){valid= false;}
                }catch(InputMismatchException e){
                    out.print("please input valid coordinates: like 1,2 or 0,2");
                    valid=false;
                }catch(NoSuchElementException e1){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }
                if (to >2|| from==to){valid= false;
                    out.print("please input valid positions: like 1,2 or 0,2");
                }
            }
        } while (!valid);
        int finalTo = to;
        int finalFrom = from;
        notifyObservers(obs -> obs.onSwap(finalTo, finalFrom));
    }

    /**
     * asks player to select in which column of their shelf to insert the tiles in hand
     * @author Andrea Migliorini
     */
    @Override
    public void askinsertcol(){
        int col=9;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("input \"x\" the column in which to insert the tiles in your hand, they will be inserted with the rightmost as the one at the bottom");
        do {
            try{valid=true;
                col =in.nextInt();}
            catch(InputMismatchException e){
                out.print("please input a valid column (a number from 0 to 4)");
                valid=false;
            }catch(NoSuchElementException e1){
                out.print("please input a valid column (a number from 0 to 4)");
                valid=false;
            }
            if(col>4||col<0){
                out.print("please input a valid column (a number from 0 to 4)");
                valid=false;
            }
            } while(!valid);
        int finalCol = col;
        notifyObservers(obs ->obs.onSelectCol(finalCol));
    }


    @Override
    /**
     * *prints the board to the player in color
     @param Space[][] the game Board
      * @author Samuele Mazzoleni
     * @author Andrea Migliorini
     **/
    public void boardshow (Space[][] board){
        out.print("-");
        for (int i=0;i<board.length;i++) {
            out.print("  "+i);
        }
        out.print("\n");
        for(int i = 0; i < board.length; i++) {
            out.print(i);
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j].getTile().isPresent()) {
                    switch (board[i][j].getTile().get().getType()) {
                        case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            out.print("\n");
        }
    }


    @Override
    /**
     * *prints the board to the player in color
     @param Optional[][]<Tile> shelf
      * @author Pier Antonio Mauro
     * @author Andrea Migliorini
     **/
    public void shelfshow(Optional<Tile>[][] shelf) {
        out.print("- 0  1  2  3  4  5\n");
        for (int i = 0; i < shelf.length; i++) {
            out.print(i);
            for (int j = 0; j < shelf[0].length; j++) {
                if (shelf[i][j].isPresent()) {
                    switch (shelf[i][j].get().getType()) {
                        case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                } else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * show tiles in player hand
     * @param hand ArrayList<Tile>
     * @author Andrea Migliorini
     */
    @Override
    public void showtilesinhand(ArrayList<Tile> hand) {
        out.println("0 is the tile placed at the bottom of the column");
        for(int i=0;i<hand.size(); i++){
            out.print(" "+i+" ");
        }
        out.print("\n");
        for (Tile tile : hand) {
            switch (tile.getType()) {
                case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
            }
        }

    }


    private String ReadText(){
        input = new Scanner(System.in);
        return(input.next());
    }

    private int ReadInt(){
        input = new Scanner(System.in);
        return(input.nextInt());
    }

    @Override
    public void askplayernumber() {
        int playernumber =10;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("input \"x\" number of total players in the game");
        do {
            try{valid=true;
                playernumber =in.nextInt();}
            catch(InputMismatchException e){
                out.print("please input a number of players (a number from 2 to 4)");
                valid=false;
            }catch(NoSuchElementException e1){
                out.print("please input a number of players (a number from 2 to 4)");
                valid=false;
            }
            if(playernumber>4||playernumber<2){
                out.print("please input a number of players (a number from 2 to 4)");
                valid=false;
            }
        } while(!valid);
        int finalNum = playernumber;
        notifyObservers(obs ->obs.onPlayerNumberReply(finalNum));
    }
    @Override
    public void showpoints(int points){ //Map<String, int>, Map<String, boolean[]>

        //player1 -> points: 20 | obj1 = tick | obj2 = cross

        out.print("Congratulations! You Scored:  ");
        out.print("\u001B[32m"+points+"!\n"+"\u001B[0m");
    }
}
