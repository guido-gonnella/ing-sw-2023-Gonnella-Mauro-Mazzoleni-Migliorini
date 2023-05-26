package it.polimi.ingsw.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

    private final Server server;
    private final int port;
    private ServerSocket serverSocket;
    private OutputStream out;
    private InputStream in;

    /**
     * Contructor of the class, given the port creates a SocketServer connected to that port
     * @param port
     */
    public SocketServer(Server server ,int port){
        this.server = server;
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println("Error in server creation");
            System.exit(1);
        }
    }

    public void run(){
        try {
            serverSocket = new ServerSocket(port);
            Server.LOGGER.info(() -> "Socket server: ON \n Connected to: " + port + " port.");
        } catch (IOException e) {
            Server.LOGGER.severe("Error in starting server");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                client.setSoTimeout(6000);

                ClientHandler clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread(clientHandler, "ss_handler" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                Server.LOGGER.severe("Connection lost");
            }
        }
    }

    /**
     * Method that handles the adding of a new client to the game
     * @param username client's username
     * @param clientHandler client's clientHandles class
     */
    public void addClient(String username, ClientHandler clientHandler){
        //server.addClient(username, clientHandler);
    }
/*
    public ServerSocket getServerSocket(){
        return this.socket;
    }*/
    public void sendAck(Socket socket){
        try{
        out = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeBoolean(true);
        dataOutputStream.flush();
        }catch (IOException e){
            System.err.println("Error in sending ack");
            System.exit(1);
        }
    }

    public void sendNack(Socket socket){
        try{
            out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBoolean(false);
            dataOutputStream.flush();
        }catch (IOException e){
            System.err.println("Error in sending nack");
            System.exit(1);
        }
    }

    public String readUsername(Socket socket){
        try {
            in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            return new String(buffer, 0, bytesRead);
        }catch(IOException e){
            System.err.println("Error receiving string");
            System.exit(1);
            return null;
        }
    }

    /**
     * Notify to the server that a client disconnected.
     *
     * @param clientHandler the clientHandler associated to the client disconnected
     */
    public void onDisconnection(ClientHandler clientHandler) {
        server.onDisconnection(clientHandler);
    }

}
