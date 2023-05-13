package it.polimi.ingsw.Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ServerHandler {

    Set<String> usernames;
    Map<Set<String>, Server> mapPlayerServer;
    int port;
    ServerSocket serverSocket;
    SocketServer socketServer;


    public ServerHandler(int port){
        this.port = port;
        usernames = null;
    }

    public void init(){
        usernames = null;
        mapPlayerServer = null;
        Server server = new Server(port);
        this.socketServer = new SocketServer(port);
    }


    public void clientConnect(Socket socket){

    }

    /**
     * Check the username used by the client isn't already in use
     * @param socket of the client
     */
    public void collectUsername(Socket socket){
        String username = socketServer.readUsername(socket);
        if(!usernames.isEmpty()){
            while(usernames.contains(username)){
                socketServer.sendNack(socket);
                username = socketServer.readUsername(socket);
            }
        }
        socketServer.sendAck(socket);
        usernames.add(username);
        addClientToGame(username, socket);
    }

    /**
     * Adds the client to a game where isn't already reached the max number or players,
     * otherwise creates a new game
     * @param username
     * @param socket
     */
    public void addClientToGame(String username, Socket socket){
        if(!mapPlayerServer.isEmpty()){
            for(Server s : mapPlayerServer.values()){
                if(s.getNumPlayers() < s.getMaxPlayer()){
                    s.addClient(username, socket);
                    return;
                }
            }
        }
        Server server = new Server(port);
        server.addClient(username, socket);
    }

    /**
     * Check if the max number of players is valid
     * @param max num of player
     * @return
     */
    public boolean validNumberPlayers(int max){
        return max >= 2 && max <= 4;
    }
}
