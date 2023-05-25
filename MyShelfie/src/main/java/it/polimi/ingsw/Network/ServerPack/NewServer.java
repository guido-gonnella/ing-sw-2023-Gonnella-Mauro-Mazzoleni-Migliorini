package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Controller.NewGameController;

import java.util.Collection;
import java.util.HashSet;

/**
 * Class that serves as server and runs the game
 */
public class NewServer implements Runnable{

    private Collection<String> usernames;
    private int numPlayers;
    private int maxNumPlayers;
    private int port;

    public NewServer(int port){
        this.port = port;
        this.usernames = new HashSet<>();
        this.numPlayers = 0;
        this.maxNumPlayers = 0;
    }
    @Override
    public void run() {

    }
}
