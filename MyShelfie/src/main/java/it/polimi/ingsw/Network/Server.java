package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {

    ServerSocket serverSocket;
    SocketServer socketServer;
    GameController gameController;
    Map<String, Client> mapPlayerClient;

    int maxPlayer;
    int numPlayer;

    public Server(int port){
        this.socketServer = new SocketServer(port);
        this.serverSocket = socketServer.getServerSocket();
        numPlayer = 0;
        maxPlayer = 0;
    }

    /**
     * Add the client to the game
     * @param username
     * @param clientSocket
     */
    public void addClient(String username, Socket clientSocket){
        Client client = new Client(clientSocket);
        mapPlayerClient.put(username, client);
        numPlayer++;
    }

    /**
     * Getter of numPlayer
     * @return
     */
    public int getNumPlayers(){
        return numPlayer;
    }

    /**
     * Getter of maxPlayer
     * @return
     */
    public int getMaxPlayer(){
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer){
        this.maxPlayer = maxPlayer;
    }

    /**
     * Manages the disconnection of a client by notifying the associated client of the error.
     * Create a new clientHandler map to handle subsequent reconnection.
     *
     * @param clientHandler the clientHandler of the player who disconnected
     */
    public void onDisconnection(ClientHandler clientHandler) {

    }
}
