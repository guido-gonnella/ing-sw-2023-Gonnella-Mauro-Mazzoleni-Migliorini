package it.polimi.ingsw.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable{

    private final Socket client;
    private final SocketServer socketServer;
    private boolean connected;
    private transient ObjectOutputStream output;
    private transient ObjectInputStream input;
    private transient int timer;
    private transient boolean enableTimer;
    private transient final Object lock;
    private transient String nickname;

    public ClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.lock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method that manages the launch of the thread that deals with
     * the method for handling messages.
     */
    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            System.err.println("Client " + client.getInetAddress() + " connection dropped, client handler");
            disconnect();
        }
    }

    /**
     * Handles the connection of a new client and keep listening to the socket for
     * new messages from the specific client related.
     *
     * @throws IOException any of the usual Input/Output related exceptions.
     */
    //da adattare al nostro codice
    private void handleClientConnection() throws IOException, ClassNotFoundException {
        /*System.err.println("Client connected from " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {
                String msg = (String) input.readObject();

                CommandMessage message = CommandSerializer.deserialize(msg);

                if (message != null && message.getType() != MessageType.PONG) {

                    if (message.getType() == MessageType.NEW_GAME || message.getType() == MessageType.NICKNAME) {
                        socketServer.addClient(message, this);
                    }
                    else if(message.getType() == MessageType.CHOSEN_RESTORE_GAME){
                        socketServer.restoreGame(message, this);
                    }
                    else if(message.getType() == MessageType.CHAT_MESSAGE_CLIENT_SERVER){
                        socketServer.chat(message);
                    }
                    else if(message.getType() == MessageType.RELOAD_MESSAGES) {
                        if(nickname == null)
                            nickname = message.getNickname();
                        synchronized (lock) {
                            enableTimer = true;
                            timer = 0;
                        }
                    }
                    else {
                        socketServer.receiveMessage(message);
                        if(nickname == null)
                            nickname = message.getNickname();
                    }
                }

            }
        } catch (ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Invalid stream from client");
            throw e;
        }
        catch (SocketException e) {
            System.err.println("Client disconnected");
            throw e;
        }
        client.close();*/
    }

    /**
     * Disconnect the socket.
     */
    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                System.out.println("Client disconnected");
            }
            connected = false;
        }

        //da aggiustare
        socketServer.onDisconnection(this);
        Thread.currentThread().interrupt();
    }
}
