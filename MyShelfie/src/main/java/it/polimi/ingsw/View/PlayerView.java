package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SerializableOptional;
import it.polimi.ingsw.Model.Tile;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used to print the outputs on the CLI
 * @author Pierantonio Mauro
 */

public class PlayerView {

    private final Player player;
    public PlayerView(Player player){
        this.player = player;
    }

    /**
     * Method that prints the shelf of a player
     * @author Pierantonio Mauro
     */
    public void printShelf(){
        SerializableOptional<Tile>[][] shelf = player.getShelf().getShelf();

        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(shelf[i][j].isPresent()){
                    switch (shelf[i][j].get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "T" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "F" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "P" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "G" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "B" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "C" + "\u001B[0m");
                        default -> System.out.print(" ");
                    }
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }
    /**
     * Method to print the tiles that a player have in hand
     * @param tiles
     * @author Pierantonio Mauro
     */
    public void printTilesInHand(ArrayList<Tile> tiles){
        for(Tile t : tiles){
            System.out.print("[" + t.getType() + "]");
        }
        System.out.print("\n");
    }

    public String takeAddress(){
        Scanner scanner = new Scanner(System.in);
        String address;
        System.out.print("Select the address: ");
        address = scanner.next();
        return address;
    }
    public int takePort(){
        Scanner scanner = new Scanner(System.in);
        int port = 4999;
        System.out.print("Select the port: ");
        port = scanner.nextInt();
        return port;
    }

    public static String takeNickname(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select your nickname: ");
        return scanner.next();
    }
}
