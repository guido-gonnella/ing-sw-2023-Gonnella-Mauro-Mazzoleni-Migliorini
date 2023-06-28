package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * This class is used to communicate with the server, is purpose is to act as a blackbox
 * for the NetworkHandler, which receives and sends messages
 */
public class ClientSocket extends ClientConnection {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Constructor of the class.<br>
     * It creates the connection with the server on {@link Socket socket} attribute.
     * @param address the server IPv4 address.
     * @param port the port of the server.
     */
    public ClientSocket(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.socket.setSoTimeout(250000);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        }catch(SocketTimeoutException e){
            System.out.print("The server didn't respond in time.\n");
            //disconnect();
        } catch (IOException e) {
            System.out.print("Something went wrong...\n");
            //disconnect();
            //e.printStackTrace();
        }

    }

    /**
     * Read a message from the server and returns it to the NetworkHandler
     * @return the message received
     */
    public synchronized Message readMessage(){
            try {
                Message messageArrived = (Message) input.readObject();
                if (messageArrived != null) {
                    return messageArrived;
                }
            }catch(SocketTimeoutException e){
                System.out.print("The server didn't respond in time\n");
                disconnect();
                //return new ErrorMessage("Error in receiving the message\n");
            }catch (IOException | ClassNotFoundException e) {
                if(!socket.isClosed())
                    disconnect();
                System.out.print("The server didn't respond in time\n");
                //return new ErrorMessage("Error in reading message\n");
            }
            if(!socket.isClosed())
                disconnect();
            System.out.print("The server didn't respond in time\n");
            //return new ErrorMessage("Error in reading message\n");
            return null;
    }

    /**
     * Send a message to the server, if there is an exception, the networkHandler will inform the player,
     * disconnect the client and end the game
     * @param message sent to the server
     */
    public synchronized void sendMessage(Message message) {
            try {
                output.writeObject(message);
                output.flush();
                output.reset();
            }catch(SocketTimeoutException e){
                System.out.print("The client didn't respond in time, connection closed\n");
                if(!socket.isClosed())
                    disconnect();
            }catch (IOException e) {
                if(!socket.isClosed())
                    disconnect();
                System.out.print("An error occurred\n");
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
            System.out.print("An error occurred while closing the connection\n");
        }
    }
}
