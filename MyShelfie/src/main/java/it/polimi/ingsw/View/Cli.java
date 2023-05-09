package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ViewObservable;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cli extends ViewObservable implements View{
    private final PrintStream out;
    private Scanner input;
    private String temp;
    Cli(){out= System.out;
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
    @Override
    public  void init(){
        String serverAddr;
        int port=8080;
        out.print("please input the ip adress of the server");
        Scanner in=new Scanner(System.in);
        serverAddr=Readin();
        Boolean valid=true;
        do {
            out.print("please input the port of the server");
            try{ port =in.nextInt();
            }catch(InputMismatchException e){
                out.print("please input a valid port ");
                valid=false;
            }catch(NoSuchElementException e){
                out.print("please input a valid port");
                valid=false;
            }

        } while (!valid);
        notifyObservers(obs -> obs.onConnection(serverAddr,port));

    }

    @Override
    public void asknickname() {
        out.print("First Insert your Username: ");
        temp=Readin();
        notifyObservers(obs->obs.UpdateNickname(temp));
    }
    @Override
    public void askselecttile(){
            Integer y = -1, x = -1;
            boolean valid =true;
            Scanner in = new Scanner(System.in);
            out.print("input \"x,y\" coordinates of the tile, to stop input \"-1,-1\"");
            do {
                try{ y =in.nextInt();
                }catch(InputMismatchException e){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }catch(NoSuchElementException e){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }

                if( valid){
                    try{
                        x =in.nextInt();
                    }catch(InputMismatchException e){
                        out.print("please input valid coordinates: like 1,2 or 0,3");
                        valid=false;
                    }catch(NoSuchElementException e){
                        out.print("please input valid coordinates: like 1,2 or 0,3");
                        valid=false;
                    }
                }
            } while (!valid);
        Integer finalX = x;
        notifyObservers(obs -> obs.onSelectTile(finalX, y));

    }
    @Override
    public void askswap(){
        int to=4, from=4;
        boolean valid =true;
        Scanner in = new Scanner(System.in);
        out.print("input \"x,y\" the first tile and to which position to move it in, to confirm the order of the tiles type \"-1,-1\"");
        do {
            try{from =in.nextInt();}
            catch(InputMismatchException e){
                out.print("please input valid positions: like 1,2 or 0,2");
                valid=false;
            }catch(NoSuchElementException e){
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
                }catch(NoSuchElementException e){
                    out.print("please input valid coordinates: like 1,2 or 0,3");
                    valid=false;
                }
                if (to >2|| from==to){valid= false;
                    out.print("please input valid positions: like 1,2 or 0,2");
                }
            }
        } while (!valid);
        notifyObservers(obs -> obs.onSwap(to, from));
    }




    private String Readin(){
        input=new Scanner(System.in);
        return(input.next());
 }

}
