package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Tile;

import java.util.ArrayList;
import java.util.Optional;
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
        Optional<Tile>[][] shelf = player.getShelf().getShelf();

        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(shelf[i][j].isPresent()){
                    System.out.println("[");
                    System.out.println(shelf[i][j].get());
                    System.out.println("]");
                }
                else{
                    System.out.println("[");
                    System.out.println(" ");
                    System.out.println("]");
                }
                System.out.println("%n");
            }
        }
    }

    /**
     * Method to print the tiles that a player have in hand
     * @param tiles
     * @author Pierantonio Mauro
     */
    public void printTilesInHand(ArrayList<Tile> tiles){
        for(Tile t : tiles){
            System.out.println("[");
            System.out.println(t.getType());
            System.out.println("]");
        }
        System.out.println("%n");
    }

    public String takeAddress(){
        Scanner scanner = new Scanner(System.in);
        String address;
        System.out.println("Select the address: ");
        address = scanner.next();
        return address;
    }
    public int takePort(){
        Scanner scanner = new Scanner(System.in);
        int port = 4999;
        System.out.println("Select the port: ");
        port = scanner.nextInt();
        return port;
    }
}
