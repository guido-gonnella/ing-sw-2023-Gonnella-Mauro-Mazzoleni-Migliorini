package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.ViewObservable;

import java.io.PrintStream;
import java.util.*;


/**
 * View by commandline
 * @author Andrea Migliorini
 */
public class Cli extends ViewObservable implements View{
    private PrintStream out;
    private Scanner input;
    private String temp;
    public Cli(){
        out= new PrintStream(System.out);
        out.println("""
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
    public void init(){
        String serverAddr = "localhost"; //default value
        int port = 8080; //default value
        boolean valid;
        do {
            out.print("Please input the address of the server: ");
            try{
                serverAddr = ReadText();
                if(serverAddr.equals("")){
                    valid = false;
                }
                else valid = NetworkHandler.isValidIpAddress(serverAddr);
            } catch(NoSuchElementException e) {
                valid = false;
            }
            if(!valid) out.println("Please input a valid address!");
        } while (!valid);

        do {
            out.print("Please input the port of the server: ");
            try{
                port = ReadInt();
                valid = NetworkHandler.isValidPort(port);
            } catch(NoSuchElementException e1){
                valid = false;
            }
            if(!valid) out.println("Please input a valid port!");
        } while (!valid);

        int finalPort = port;
        String finalServerAddr = serverAddr;

        notifyObservers(obs -> obs.onConnection(finalServerAddr, finalPort));
    }

    @Override
    public void askNickname() {
        out.print("First insert your username: ");
        String nickname = ReadText();
        notifyObservers(obs->obs.onNicknameUpdate(nickname));
    }

    /**
     * asks user to select tile, no checks on board limits
     * @author Andrea Migliorini
     */
    @Override
    public void askSelectTile(){
        int[] coords = new int[2];
        boolean valid;
        String temp;
        Scanner in = new Scanner(System.in);
        out.print("Input \"ROW,COL\" coordinates of the tile, to stop input \"-1,-1\": ");
        do {
            try{
                valid = true;
                temp = in.nextLine();
                coords = extractIntegers(temp);
                if(coords == null) {
                    out.print("Error, please input valid coordinates (like \"1,2\" or \"0,3\"): ");
                    valid = false;
                }
            }catch(InputMismatchException e){
                out.print("Mismatch, please input valid coordinates (like \"1,2\" or \"0,3\"): ");
                valid = false;
            }catch(NoSuchElementException e1){
                out.print("No element, please input valid coordinates (like \"1,2\" or \"0,3\"): ");
                valid = false;
            }
        } while (!valid);

        int finalROW = coords[0];
        int finalCOL = coords[1];
        notifyObservers(obs -> obs.onSelectTile(finalROW,finalCOL));
    }

    //TODO nuovo, da togliere o modificare
    @Override
    public void askSelection(Board board, Shelf shelf){
        int chosenCol;
        int tempCol;
        ArrayList<Tile> chosenTiles = new ArrayList<>();
        ArrayList<Coords> chosenCoords = new ArrayList<>();
        String temp;
        int[] coords = new int[2];
        boolean end = false;

        //do{
        // chosenCoords.clear();
        // chosenTiles.clear();
        for(int i = 0; i<3 && !end; i++){
            showText("Input \"ROW,COL\" coordinates of the tile, to stop input \"-1,-1\": ");
            try {
                coords = extractIntegers(ReadText());
                if(coords[0] == -1 && coords[1] == -1){
                    end = true;
                } else {
                    chosenCoords.add(new Coords(coords[0], coords[1]));
                    chosenTiles.add(board.getGrid()[coords[0]][coords[1]].getTile().get());
                }
            }catch(InputMismatchException e){
                showText("Mismatch please input valid coordinates (like \"1,2\" or \"0,3\"): ");
                i--;
            }catch(NoSuchElementException e1){
                showText("No element please input valid coordinates (like \"1,2\" or \"0,3\"): ");
                i--;
            }
        }
        //}while(selezione non valida, fare check);

        showTilesInHand(chosenTiles);

        showText("Select the column [0 to 4] ");
        tempCol = ReadInt();
        while(tempCol < 0 || tempCol > 4){ //oppure le tiles non entrano nella colonna
            showText("Out of bounds, select a number between 0 and 4 ");
            tempCol = ReadInt();
        }
        chosenCol = tempCol;

        notifyObservers(obs -> obs.onSelection(chosenCoords, chosenCol));

    }

    /**
     * asks to order the tiles to insert the colums, checks position outside of max size of hand and different position of tiles
     * @author Andrea Migliorini
     */

    @Override
    public void invalidTile(int ROW,int COL) {
        out.print("Tile in " + ROW + "," + COL + " doesn't exist\n");
    }

    @Override
    public void invalidCombo() {
        out.print("Invalid tiles combination selected, please select exposed and adjacent tiles\n");
    }

    @Override
    public void invalidColumn(int column) {
        out.print("The empty spaces in column " + column + " are not enough, try again\n");
    }

    /**
     * asks player to select in which column of their shelf to insert the tiles in hand
     * @author Andrea Migliorini
     */
    @Override
    public void askInsertCol(){
        int col=0;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("Input the column in which to insert the tiles in your hand, they will be inserted with the leftmost as the one at the bottom: ");
        do {
            try{
                valid=true;
                col =in.nextInt();}
            catch(NoSuchElementException e){
                out.print("Please input a valid column (a number from 0 to 4): ");
                valid=false;
            }
            if(col>4 || col<0){
                out.print("Please input a valid column (a number from 0 to 4): ");
                valid=false;
            }
        } while(!valid);
        int finalCol = col;
        notifyObservers(obs -> obs.onSelectCol(finalCol));
    }


    /**
     * *prints the board to the player in color
     @param board the game Board
      * @author Samuele Mazzoleni
     * @author Andrea Migliorini
     **/
    @Override
    public void boardShow(Space[][] board){
        out.print("-");
        for (int i=0;i<board.length;i++) {
            out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
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


    /**
     * *prints the board to the player in color
     @param shelf<Tile>[][] shelf
      * @author Pier Antonio Mauro
     * @author Andrea Migliorini
     **/
    @Override
    public void shelfShow(SerializableOptional<Tile>[][] shelf) {
        out.print("-");
        for (int i=0;i<5;i++) {
            out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
        }
        out.print ("\n");
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
    public void showTilesInHand(ArrayList<Tile> hand) {
        out.println("0 is the first tile that will be put in the shelf\n");
        for(int i=0;i<hand.size(); i++){
            out.print(" " + i + " ");
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
        out.println("\n");
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
    public void askPlayerNumber() {
        int playerNumber;
        boolean valid;
        Scanner in = new Scanner(System.in);
        out.print("Input the number of total players in the game: ");

        do {
            valid=true;
            playerNumber = in.nextInt();

            if(playerNumber>4 || playerNumber<2) {
                out.print("Please input a valid player number (a number from 2 to 4): ");
                valid=false;
            }
        } while(!valid);

        final int maxPlayers = playerNumber;
        notifyObservers(obs -> obs.onPlayerNumberReply(maxPlayers));
    }
    @Override
    public void showPoints(Map<String, Integer> mapPoints, Map<String, boolean[]> mapObjective){
        //Map<String, int>, Map<String, boolean[]>
        //player1 -> points: 20 | obj1 = tick | obj2 = cross
        boolean flag;
        for (String player: mapPoints.keySet())
        {
            flag= false;
            out.print("\n" + "\u001B[35m" + player + "\u001B[0m" + " scored: ");
            out.print("\u001B[33m" + mapPoints.get(player) + "\u001B[0m" + " points!\n");
            out.print("and completed: ");
            if (mapObjective.get(player)[0]){
                flag=true;
                out.print("\u001B[32m" + "the first objective ✔" + "\u001B[0m");
            }
            if (flag) {
                if (mapObjective.get(player)[1]) {
                    out.print("\u001B[32m" + " and the second objective ✔" + "\u001B[0m");
                }
            } else if (mapObjective.get(player)[1]) {
                out.print("\u001B[32m" + "the second objective ✔" + "\u001B[0m");
            } else {
                out.print("\u001B[31m" + "no objectives (╥﹏╥)" + "\u001B[0m");
            }
        out.print("\n");
        }
    }

    @Override
    public void showPublicObjective(PubObjType code) {
        switch (code)
        {
            case ANGLES -> out.println("Place four of the same tiles on the corner of the shelf\n\t[=]---[=]\n\t |    |\n |    |\n\t[=]---[=]\n");
            case SIX_COUPLES -> out.println("Place six groups of at least 2 tiles of the same type\n\t{[=][=]} x6\n");
            case FOUR_QUADRUPLES -> out.println("Place four groups of at least 4 tiles of the same type\n\t{[=][=][=][=]} x4\n");
            case TWO_SQUARES -> out.println("Place 2 groups of 4 tiles in a square of 2x2 tiles\n\t[=][=]\n\t[=][=] x2\n");
            case COL_THREE_TYPES -> out.println("Place 3 groups of 6 tiles in a column, it can have at most 3 different types of tiles\n\t[■]\n\t[■]\n\t[■] MAX 3 [≠]\n\t[■] x3\n\t[■]\n\t[■]\n");
            case CROSS -> out.println("Place a group of 5 tiles in a X pattern\n\t[=][■][=]\n\t[■][=][■]\n\t[=][■][=]\n");
            case EIGHT -> out.println("Place 8 tiles of the same type\n\t[=] x8\n");
            case DIFF_COL -> out.println("Place 2 groups of 6 tiles of different types in a column\n\t[≠]\n\t[≠]\n\t[≠] x2\n\t[≠]\n\t[≠]\n\t[≠]\n" );
            case DIFF_ROW -> out.println("Place 2 groups of 5 tiles of different types in a line\n\t[≠][≠][≠][≠][≠] x2\n");
            case DIAG -> out.println("Place 5 tiles in a diagonal\n\t[=]\n\t   [=]\n\t      [=]\n\t         [=]\n\t            [=]\n");
            case ROW_THREE_TYPES -> out.println("Place 4 groups of 5 tiles in a line, it can have at most 3 different types of tiles\n\t[■][■][■][■][■]\n\t   MAX 3 [≠]\n\t      x4\n");
            case STAIR -> out.println("Place 5 columns of increasing height from left to right or right to left, starting from a height of 1\n\t[■]\n\t[■][■]\n\t[■][■][■]\n\t[■][■][■][■]\n\t[■][■][■][■][■]\n");
            default -> out.println("Invalid objective\n");
        }
    }

    @Override
    public void showPrivateObjective(PrivateObjective objective) {
        out.print("-");
        for (int i = 0; i < 5; i++) {
            out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
        }
        out.print ("\n");
        //prima z era 0, adesso non da problemi nel mostrare gli obiettivi privati
        int z = 0;
        for (int i = 0; i < 6; i++) {
            out.print(i);
            for (int j = 0; j < 5; j++) {
                if (objective.getObjective().get(z).getROW() == i && objective.getObjective().get(z).getCOL() == j){
                    switch (objective.getObjective().get(z).getType()) {
                        case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                    if(z<5) z++;
                }else {
                    out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            out.print("\n");
        }
    }

    public static int[] extractIntegers(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            return null; // Invalid input
        }

        int[] values = new int[2];
        try {
            values[0] = Integer.parseInt(parts[0].trim());
            values[1] = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            return null; // Invalid input
        }

        return values;
    }

    public void showText(String text){
        out.print(text);
    }
}
