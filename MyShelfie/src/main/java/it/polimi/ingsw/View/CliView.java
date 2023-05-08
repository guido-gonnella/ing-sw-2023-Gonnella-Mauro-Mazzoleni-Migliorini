package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ViewObservable;

import java.io.PrintStream;
import java.util.Scanner;

public class CliView extends ViewObservable implements View{
    private final PrintStream out;
    private Scanner input;
    CliView(){out= System.out;}
    public  void init(){
        input=new Scanner(System.in);
        //copy asci art code



    }

}
