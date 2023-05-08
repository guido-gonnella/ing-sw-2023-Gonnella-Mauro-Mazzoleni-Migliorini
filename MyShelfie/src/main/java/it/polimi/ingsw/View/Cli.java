package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ViewObservable;

import java.io.PrintStream;
import java.util.Scanner;

public class Cli extends ViewObservable implements View{
    private final PrintStream out;
    private Scanner input;
    private String temp;
    Cli(){out= System.out;}
    @Override
    public  void init(){

        out.print(" __          __  _                            _______                         _____ _          _ ______       \n" +
                " \\ \\        / / | |                          |__   __|                       / ____| |        | |  ____(_)     \n" +
                "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___     | | ___    _ __ ___  _   _  | (___ | |__   ___| | |__   _  ___ \n" +
                "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\    | |/ _ \\  | '_ ` _ \\| | | |  \\___ \\| '_ \\ / _ \\ |  __| | |/ _ \\\n" +
                "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/    | | (_) | | | | | | | |_| |  ____) | | | |  __/ | |    | |  __/\n" +
                "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|    |_|\\___/  |_| |_| |_|\\__, | |_____/|_| |_|\\___|_|_|    |_|\\___|\n" +
                "                                                                      __/ |                                    \n" +
                "                                                                     |___/                                     ");

    }

    @Override
    public void asknickname() {
        out.print("First Insert your Username: ");
        temp=Readin();
        notifyObservers(obs->obs.UpdateNickname(temp));
    }
    @Override
    public void askselecttile(){
        out.print("input \"x,y\" coordinates of the tiles");
        Scanner in= new Scanner(System.in);
        int y=in.nextInt();
        int x=in.nextInt();
        notifyObservers(obs->obs.onSelectTile(x,y));
    }

    private String Readin(){
        input=new Scanner(System.in);
        return(input.next());
 }

}
