package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.View.Cli;

import java.net.Socket;

/**
 * Class that handle the communication client-side
 */
public class Clien {

    private int port;
    private Socket socket;
    private SocketServ socketServ;
    private Cli cli;
    private String username;

    /**
     * Constructor of the class
     * @param socket
     */
    public Clien(Socket socket, int port){
        this.port = port;
        this.socket = socket;
        this.socketServ = new SocketServ(socket, port);
    }

    /**
     * Getter of the socket
     * @return
     */
    public Socket getSocket(){ return this.socket;}

    /**
     * Setter of the username
     * @param username
     */
    public void setUsername(String username){ this.username = username;}

}
