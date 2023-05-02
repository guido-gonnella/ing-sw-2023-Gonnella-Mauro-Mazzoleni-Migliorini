package it.polimi.ingsw.View;
import java.io.*;
import java.util.*;
/**
 To use at the start of player controller
 @author Andrea Migliorini
 */
public class Cli {
    public String c;
    public Cli() {
        Scanner in = new Scanner(System.in);
        c = "0";
        System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
        c = in.next();
        while (!(c.charAt(0) == 's' || c.charAt(0) == 'r' || c.charAt(0) == 'S' || c.charAt(0) == 'R')) {
                System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
            c =in.next();
        }
        System.out.println("Starting Connection process...");

    }

    public String SetUsername(){
        String nickname= new String();
        Scanner scanner = new Scanner(System.in);
        System.out.println("First Insert your Username: ");
        nickname= scanner.next();
        return (nickname);
    }

}
