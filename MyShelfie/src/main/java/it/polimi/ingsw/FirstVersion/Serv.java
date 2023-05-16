package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Server;

import java.net.Socket;
import java.util.Set;
import java.util.logging.Logger;

import static it.polimi.ingsw.FirstVersion.MessagesAAA.*;

/**
 * Class used to handle the server-side communication
 */
public class Serv implements Runnable{

    private GameController gameController;
    private Set<Clien> clients;
    private int maxNumPlayer;
    private int numPlayer;
    private SocketServ socketServ;
    private int port;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /*public Serv(Clien client, int port){
        this.socketServ = new SocketServ(client.getSocket(), port);
        clients.add(client);
        readNumPlayers(client.getSocket());
        numPlayer = 1;
    }*/

    /**
     * Read the max number of players and ensures that is valid
     * @param socket of the client
     */
    /*private void readNumPlayers(Socket socket){
        socketServ.sendMessage(socket, GET_NUM_PLAYERS);
        maxNumPlayer = socketServ.readInt(socket);
        while(maxNumPlayer <= 1 || maxNumPlayer > 4){
            socketServ.sendNack(socket);
            maxNumPlayer = socketServ.readInt(socket);
        }
        socketServ.sendNack(socket);
    }*/

    @Override
    public void run() {
        this.gameController = new GameController();
        // fa iniziare la partita
    }

    /**
     * Adds a client to the server
     * @param client
     */
    public void addClient(Clien client){
        this.clients.add(client);
        numPlayer++;
        if(numPlayer == maxNumPlayer){
            Thread thread = new Thread(this, "serv_");
            thread.start();
        }
    }

    public int getMaxNumPlayer(){
        return this.maxNumPlayer;
    }

    public int getNumPlayer(){
        return this.numPlayer;
    }
}
