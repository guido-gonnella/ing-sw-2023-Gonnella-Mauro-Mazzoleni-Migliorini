package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Message.ErrorMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Observer.Observable;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient extends Observable {

    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

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
     * <br>Initialize the input and output streams from the socket object
     *
     * @param port of the connection
     * @author Guido Gonnella
     */

    public SocketClient(String serverAddr, int port) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(serverAddr, port));
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * This method recieve an object from the server
     * @author Guido Gonnella
     */
    public void recieveData() {
        Message msg = null;

        try{
            msg = (Message) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            msg = new ErrorMessage(null, "Connection lost\n" );
        }

        notifyAllObservers(msg);
    }

    /**
     * send the message to the server
     * @param msg the message to be sent
     * @author Guido Gonnella
     */
    public void sendData(Message msg){
        try{
            out.writeObject(msg);
        } catch (IOException e) {
            notifyAllObservers(new ErrorMessage(null, "Cannot send the message\n"));
        }
    }

    /**
     * close the socket connection
     * @author Guido Gonnella
     */
    public void disconnect(){
        Message msg = null;
        try{
            socket.close();
        } catch (IOException e) {
            notifyAllObservers(new ErrorMessage(null, "Cannot disconnect\n"));
        }

    }
}
