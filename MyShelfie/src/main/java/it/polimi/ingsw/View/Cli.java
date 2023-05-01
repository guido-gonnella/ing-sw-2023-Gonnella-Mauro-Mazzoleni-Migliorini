package main.java.it.polimi.ingsw.View;
import java.io.*;
import java.util.*;

public class Cli {

    public Cli() {
        Scanner in = new Scanner(System.in);
        String c = "0";
        System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
        while (!(c.charAt(0) == 's' || c.charAt(0) == 'r' || c.charAt(0) == 'S' || c.charAt(0) == 'R')) {
            c = in.next();
            if (c.charAt(0) == 's' || c.charAt(0) == 'S') {

            } else if ((c.charAt(0) == 'R' || c.charAt(0) == 'r')) {
                /*start rmi process*/
            } else {
                System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
            }
        }
        System.out.println("Starting Connection process...");
    }
    public  AskConnection(){};
}
