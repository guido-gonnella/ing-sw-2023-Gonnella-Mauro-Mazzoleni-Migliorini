package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Network.Message.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that handles the communication between server and client,
 * it communicates with one client only, each client will have its
 * own realization of this class
 */
public class SocketServer extends ServerConnection {

    private final Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Constructor of the class
     * @param serverSocket of the server
     * @throws IOException
     */
    public SocketServer(ServerSocket serverSocket) throws IOException {
        this.socket = serverSocket.accept();
        this.output = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Client connected\n");
    }

    /**
     * Reads a message from the client and returns it to
     * the virtual view
     * @return
     */
    public synchronized Message readMessage(){
        try{
            InputStream is = socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(is);
            Message messageArrived = (Message) input.readObject();
            if(messageArrived != null) {
                return messageArrived;
            }
            input.close();
        }catch(IOException | ClassNotFoundException e){
            //disconnect();
            e.printStackTrace();
            return new ErrorMessage("Error in receiving the message");
        }
        return new ErrorMessage("Error in receiving the message");
    }

    public synchronized void sendMessage(Message message){
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect the socket
     */
    public synchronized void disconnect(){
        try {
            output.close();
            if (socket != null && !socket.isClosed())
                socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
