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
     System.out.println(" __          __  _                            _______                         _____ _          _ ______       \n" +
             " \\ \\        / / | |                          |__   __|                       / ____| |        | |  ____(_)     \n" +
             "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___     | | ___    _ __ ___  _   _  | (___ | |__   ___| | |__   _  ___ \n" +
             "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\    | |/ _ \\  | '_ ` _ \\| | | |  \\___ \\| '_ \\ / _ \\ |  __| | |/ _ \\\n" +
             "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/    | | (_) | | | | | | | |_| |  ____) | | | |  __/ | |    | |  __/\n" +
             "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|    |_|\\___/  |_| |_| |_|\\__, | |_____/|_| |_|\\___|_|_|    |_|\\___|\n" +
             "                                                                      __/ |                                    \n" +
             "                                                                     |___/                                     ");
        /*   System.out.println(" __          __  _                            _______                         _____ _          _ ._ _      \n" +
                " \\ \\        / / | |                          |__   __|                       / ____| |        | | /       \n" +
                "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___     | | ___    _ __ ___  _   _  | (___ | |__   ___| | |_ _  ___ \n" +
                "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\    | |/ _ \\  | '_ ` _ \\| | | |  \\___ \\| '_ \\ / _ \\ |  _| |/ _ \\\n" +
                "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/    | | (_) | | | | | | | |_| |  ____) | | | |  __/ | | | |  __/\n" +
                "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|    |_|\\___/  |_| |_| |_|\\__, | |_____/|_| |_|\\___|_|_| |_|\\___|\n" +
                "                                                                      __/ |                                 \n" +
                "                                                                     |___/                                  " );
    }*/
    }
 public char Connectionmethod(){
     Scanner in = new Scanner(System.in);
     c = "0";
     System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
     c = in.next();
     while (!(c.charAt(0) == 's' || c.charAt(0) == 'r' || c.charAt(0) == 'S' || c.charAt(0) == 'R')) {
         System.out.println("What's your Connection Method? \n Socket (Type S) or Rmi(Type R)");
         c =in.next();
     }
     if (c.charAt(0) == 's' || c.charAt(0) == 'S' ) {
         System.out.println("Starting Connection process...");
         return ('s');
     }

     if (c.charAt(0) == 'r' || c.charAt(0) == 'R' ) {
         System.out.println("Starting Connection process...");
         return ('r');
     }
     return('E');
 }

    public String SetUsername(){
        String nickname;
        Scanner scanner = new Scanner(System.in);
        System.out.println("First Insert your Username: ");
        nickname= scanner.next();
        return (nickname);
    }


}
