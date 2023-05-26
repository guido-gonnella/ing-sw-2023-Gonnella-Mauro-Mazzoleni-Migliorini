package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used to communicate with the server, is purpose is to act as a blackbox
 * for the NetworkHandler which receives and sends messages
 */
public class ClientSocket {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientSocket(String address, int port) {
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read a message from the server and returns it to the NetworkHandler
     * @return the message received
     */
    public synchronized Message readMessage(){

        try{
            this.input = new ObjectInputStream(socket.getInputStream());
            Message messageArrived = (Message) input.readObject();
            if(messageArrived != null) {
                return messageArrived;
            }
            this.input.close();
        }catch(IOException | ClassNotFoundException e){
            disconnect();
            return(new ErrorMessage("Errore nella lettura di un messaggio") );
        }
        //serve altrimenti il metodo si lamenta che manca il ritorno
        return (new ErrorMessage("Errore nella lettura di un messaggio") );
    }

    /**
     * Send a message to the server, if there is an exception the networkHandler will inform the player,
     * disconnect the client and end the game
     * @param message sent to the server
     */
    public synchronized void sendMessage(Message message) throws IOException{
        this.output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(message);
        this.output.close();
    }

    /**
     * Disconnect the socket from the server
     */
    public synchronized void disconnect(){
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
