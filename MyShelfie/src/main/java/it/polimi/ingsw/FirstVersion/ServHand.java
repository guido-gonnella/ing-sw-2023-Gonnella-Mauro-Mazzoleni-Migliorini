package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class used the create and handle the different servers for the different games
 */

public class ServHand implements Runnable{

    private final int port;
    private Set<String> usernamesList;
    private Set<Serv> serverList;
    private SocketServ socketServ;
    private ServerSocket serverSocket;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * Constructor of the class
     * @param port
     */
    public ServHand(int port){
        this.port = port;
        socketServ = null;
        usernamesList = null;
        serverList = null;
    }

    /**
     * This method start the reception of the clients and start the games when they are ready
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info(() -> "Socket server: ON \n Connected to: " + port + " port.");
        } catch (IOException e) {
            LOGGER.severe("Error in starting server");
            return;
        }

        while(!Thread.currentThread().isInterrupted()){
            try{
                Socket client = serverSocket.accept();
                client.setSoTimeout(5000);
                Clien clien = new Clien(client, port);

                String user = getUsername(clien);
                clien.setUsername(user);
                boolean avaible = false;
/*
                if(!serverList.isEmpty()){
                    for(Serv s:serverList){
                        if(s.getNumPlayer() < s.getMaxNumPlayer()) {
                            s.addClient(clien);
                            avaible = true;
                        }
                    }
                    if(!avaible){
                        Serv serv = new Serv(clien, port);
                        serverList.add(serv);
                    }
                }else{
                    Serv serv = new Serv(clien, port);
                    serverList.add(serv);
                }
*/

            }catch (IOException e){
                LOGGER.severe("Connection lost");
            }
        }
    }

    /**
     * Check if the username is valid (not "" and not already in use)
     * @param client
     */
    private String getUsername(Clien client){
        Socket clientSocket = client.getSocket();
        socketServ.sendMessage(clientSocket, MessagesAAA.GET_USERNAME);
        String user = socketServ.readUsername(clientSocket);
        while(!usernamesList.isEmpty() && (Objects.equals(user, "") || usernamesList.contains(user))){
            socketServ.sendNack(clientSocket); /* nack */
            socketServ.sendMessage(clientSocket, MessagesAAA.GET_USERNAME);
            user = socketServ.readUsername(clientSocket);
        }
        socketServ.sendAck(clientSocket); /* ack */
        usernamesList.add(user);
        return user;
    }
}
