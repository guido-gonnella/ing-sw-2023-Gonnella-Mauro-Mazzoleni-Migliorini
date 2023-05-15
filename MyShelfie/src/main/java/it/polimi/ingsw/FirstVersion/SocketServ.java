package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.Network.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Class used to communicate using the socket
 */
public class SocketServ {

    private final int port;
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private final Serv server;

    public SocketServ(Socket socket, int port){
        this.port = port;
        this.socket = socket;
        server = null;
    }

    /**
     * To send a message to the client
     * @param socket
     * @param message
     */
    public void sendMessage(Socket socket, MessagesAAA message){
        try {
            out = socket.getOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(message);
            outputStream.flush();
            outputStream.close();
        }catch(IOException e){
            System.err.println("Error in sending message");
            System.exit(1);
        }
    }

    /**
     * To receive a message to the client
     * @param socket
     */
    public void receiveMessage(Socket socket){
        try{
            in = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(in);
            MessagesAAA rec = (MessagesAAA) inputStream.readObject();
            inputStream.close();
            switch (rec){

            }
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error in receiving message");
            System.exit(1);
        }
    }

    /**
     * Send ack
     * @param socket
     */
    public void sendAck(Socket socket){
        try {
            out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.flush();
        }catch(IOException e){
            System.err.println("Error in sending ack");
            System.exit(1);
        }
    }

    /**
     * Send nack
     * @param socket
     */
    public void sendNack(Socket socket){
        try {
            out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBoolean(false);
            dataOutputStream.flush();
        }catch(IOException e){
            System.err.println("Error in sending ack");
            System.exit(1);
        }
    }

    /**
     * To read the username sent by the client
     * @return
     */
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

    public int readInt(Socket socket){

    }
}
