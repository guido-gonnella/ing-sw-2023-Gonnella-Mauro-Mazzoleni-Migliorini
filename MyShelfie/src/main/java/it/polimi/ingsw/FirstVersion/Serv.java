package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.Network.Server;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Class used to handle the server-side communication
 */
public class Serv implements Runnable{

    private Set<Clien> clients;
    private int maxNumPlayer;
    private int numPlayer;
    private SocketServ socketServ;
    private int port;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Serv(Clien client, int port){
        clients.add(client);
        //LEGGI IL NUMERO MAX DI GIOCATORI, DA QUA!!!
        numPlayer = 1;
        maxNumPlayer = 0;
    }
    @Override
    public void run() {

    }

    /**
     * Adds a client to the server
     * @param client
     */
    public void addClient(Clien client){
        this.clients.add(client);
    }

    public int getMaxNumPlayer(){
        return this.maxNumPlayer;
    }

    public int getNumPlayer(){
        return this.numPlayer;
    }
}
