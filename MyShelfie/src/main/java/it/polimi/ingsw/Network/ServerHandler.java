package it.polimi.ingsw.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ServerHandler implements Runnable {

    Set<String> usernames;
    Map<Set<String>, Server> mapPlayerServer;
    int port;
    ServerSocket serverSocket;
    SocketServer socketServer;


    public ServerHandler(int port){
        this.port = port;
        usernames = null;
    }

    /**
     * Method that opens a socket to the port indicated in the constructor. The handler will then wait
     * for the connection of new clients. Sets a timer that will expire if the handler does not receive
     * messages from a certain client, causing it to disconnect
     */
    @Override
    public void run(){
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Socket server started on port " + port + ".");
        } catch (IOException e) {
            System.err.println("Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                client.setSoTimeout(5000);

                ClientHandler clientHandler = new ClientHandler(socketServer, client);
                Thread thread = new Thread(clientHandler);
                thread.start();
                /*Socket client = serverSocket.accept();
                client.setSoTimeout(5000);

                ClientHandler clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread(clientHandler);
                thread.start();*/
            } catch (IOException e) {
                System.err.println("Connection dropped");

            }
        }
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
