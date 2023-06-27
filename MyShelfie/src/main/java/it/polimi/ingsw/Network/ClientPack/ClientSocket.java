package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.Observer.Observable.notifymessage;

/**
 * This class is used to communicate with the server, is purpose is to act as a blackbox
 * for the NetworkHandler which receives and sends messages
 */
public class ClientSocket extends ClientConnection {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final ExecutorService readExecutionQueue;


    public ClientSocket(String address, int port) {
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
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
    public  void readMessage(){
        readExecutionQueue.execute(() -> {
            while (!readExecutionQueue.isShutdown()) {

                try {
                    Message messageArrived = (Message) input.readObject();
                    if (messageArrived != null) {
                        notifymessage(messageArrived);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    disconnect();
                }
            }
        });
    }

    /**
     * Send a message to the server, if there is an exception the networkHandler will inform the player,
     * disconnect the client and end the game
     * @param message sent to the server
     */
    public synchronized void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
            output.reset();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Disconnect the socket from the server
     */
    public synchronized void disconnect() {
        try {
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
