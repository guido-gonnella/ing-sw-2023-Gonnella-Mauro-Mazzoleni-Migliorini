package it.polimi.ingsw.FirstVersion;

import java.net.Socket;

/**
 * Class that handle the communication client-side
 */
public class Clien {

    private Socket socket;
    private SocketClien socketClien;

    /**
     * Constructor of the class
     * @param socket
     */
    public Clien(Socket socket){
        this.socket = socket;
    }

    /**
     * Getter of the socket
     * @return
     */
    public Socket getSocket(){ return this.socket;}

}
