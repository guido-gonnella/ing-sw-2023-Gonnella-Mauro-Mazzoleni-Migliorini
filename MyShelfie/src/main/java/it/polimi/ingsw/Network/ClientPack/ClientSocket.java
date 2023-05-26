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
public class ClientSocket extends ClientConnection {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientSocket(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read a message from the server and returns it to the NetworkHandler
     * @return the message received
     */
    public Message readMessage(){

        try {
            synchronized (input) {
                Message messageArrived = (Message) input.readObject();
                if (messageArrived != null) {
                    return messageArrived;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnect();
            return new ErrorMessage("Error in reading message");
        }
        return new ErrorMessage("Error in reading message");
    }

    /**
     * Send a message to the server, if there is an exception the networkHandler will inform the player,
     * disconnect the client and end the game
     * @param message sent to the server
     */
    public void sendMessage(Message message) {
        synchronized (output) {
            try {
                output.writeObject(message);
                output.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Disconnect the socket from the server
     */
    public synchronized void disconnect(){try {
        if (input != null)
            input.close();
        if (output != null)
            output.close();
        if (socket != null && !socket.isClosed())
            socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
