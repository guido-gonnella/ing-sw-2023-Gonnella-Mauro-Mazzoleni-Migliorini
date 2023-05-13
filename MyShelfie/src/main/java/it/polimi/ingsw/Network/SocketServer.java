package it.polimi.ingsw.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    ServerSocket socket;
    OutputStream out;
    InputStream in;

    /**
     * Contructor of the class, given the port creates a SocketServer connected to that port
     * @param port
     */
    public SocketServer(int port){
        try {
            this.socket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println("Error in server creation");
            System.exit(1);
        }
    }

    public ServerSocket getServerSocket(){
        return this.socket;
    }
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

}
