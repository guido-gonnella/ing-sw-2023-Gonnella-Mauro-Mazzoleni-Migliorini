package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;

import java.util.Scanner;

/**
 * Class used to print game-related content
 * @author Pierantonio Mauro
 */
public class GameView {

    private final Game game;

    public GameView(Game game){
        this.game = game;
    }

    public static int takePort(){
        System.out.print("Select the port: ");
        int port = 4999; //default value for the port
        Scanner scanner = new Scanner(System.in);
        port = scanner.nextInt();
        return port;
    }

    public static String takeAddress(){
        System.out.print("Insert the address: ");
        Scanner scanner = new Scanner(System.in);
        String address = "localhost";
        address = scanner.next();
        return address;
    }
}
