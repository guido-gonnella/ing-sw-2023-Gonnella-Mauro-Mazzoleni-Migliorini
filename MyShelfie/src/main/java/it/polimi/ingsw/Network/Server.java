package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GameState;
import it.polimi.ingsw.View.Cli;
import it.polimi.ingsw.View.VirtualView;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Server {

    private ServerHandler serverHandler;
    private GameController gameController;
    private ClientHandler firstHandler;
    private boolean restored = false;
    private boolean selectedRestore = false;
    private final Object lock;
    private List<String> restoredQueue;

    private Map<String, ClientHandler> clientHandlerMap;

    private ServerSocket serverSocket;
    private SocketServer socketServer;
    private Map<String, Client> mapPlayerClient;
    int maxPlayer;
    int numPlayer;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(GameController gameController, ServerHandler serverHandler){
       // this.socketServer = new SocketServer(port);
       // this.serverSocket = socketServer.getServerSocket();
        this.gameController = gameController;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.serverHandler = serverHandler;
        numPlayer = 0;
        maxPlayer = 0;
        lock = new Object();
    }

    /**
     * Add the client to the game
     * @param username client's username
     * @param clientHandler client's clientHandler class
     */
   /* public void addClient(String username, ClientHandler clientHandler){
        //andrà modificato con una virtualview quando ci sarà la gui
        VirtualView vv = new VirtualView(clientHandler);
/*
        if(gameController.getGameState() == GameState.LOGIN){
            if(gameController.checkLoginName(username, vv)){

            }
        }

        Client client = new Client(clientSocket);
        mapPlayerClient.put(username, client);*/
        //numPlayer++;
    //}

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
