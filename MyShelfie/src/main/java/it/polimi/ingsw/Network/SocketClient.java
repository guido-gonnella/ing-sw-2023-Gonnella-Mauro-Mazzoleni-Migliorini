package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.Player;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private Player player;

        /* cose che il client manda al server:
        -nickaname (string)
        -coordinate (coord/ int, int)
        -lista di colonna, tessera (ArrayList<int, tile>)

        cose che il client riceve dal server:
        -plancia (board)
        -ack del nickname (boolean)
        -lista di tessere (ArrayList<Tile>)
        -libreria (shelf)
        -obiettivo personale (PrivateObjective)
        * */

    /**
     * Method that creates a socket connection with the server
     *
     * @param port of the connection
     * @return the socket
     * @throws IOException
     * @author Pierantonio Mauro
     */

    public SocketClient(Player player){
        this.player = player;
    }
    public Socket clientConnection(String address, int port) throws IOException {
        return new Socket(address, port);
    }

    /**
     * This method send a object to the server
     *
     * @param socket
     * @param obj sent to the server
     * @throws IOException
     * @author Pierantonio Mauro
     */
    public static void sendData(Socket socket, Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(obj);
        out.flush();
    }

    /**
     * This method receive a object from the server
     *
     * @param socket
     * @return the object received
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Pierantonio Mauro
     */
    public static Object recieveData(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return in.readObject();
    }

}
