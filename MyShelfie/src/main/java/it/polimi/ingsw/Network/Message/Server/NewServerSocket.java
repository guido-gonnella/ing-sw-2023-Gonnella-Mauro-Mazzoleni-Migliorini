package it.polimi.ingsw.Network.Message.Server;

import it.polimi.ingsw.Network.Message.ErrorMessage;
import it.polimi.ingsw.Network.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class that handles the communication between server and client,
 * it communicates with one clinet only, each client will have its
 * own realization of this class
 */
public class NewServerSocket {

    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    /**
     * Constructor of the class
     * @param socket of the client
     * @throws IOException
     */
    public NewServerSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Reads a message from the client and returns it to
     * the virtual view
     * @return
     */
    public Message readMessage(){
        try{
            Message messageArrived = (Message) input.readObject();
            if(messageArrived != null) {
                return messageArrived;
            }
        }catch(IOException | ClassNotFoundException e){
            disconnect();
            return new ErrorMessage("SERVER", "Error in receiving the message");
        }
        //serve altrimenti il metodo si lamenta che manca il ritorno
        return null;
    }

    public void sendMessage(Message message) throws IOException{
        output.writeObject(message);
    }

    /**
     * Disconnect the socket
     */
    public void disconnect(){
        try {
            output.close();
            input.close();
            if (socket != null && !socket.isClosed())
                socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
