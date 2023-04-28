package it.polimi.ingsw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

    }


    /**
     * Method that creates a socket connection
     *
     * @param port of the connection
     * @return the socket
     * @throws IOException
     * @author Pierantonio Mauro
     */
    public Socket serverConnection(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        return serverSocket.accept();
    }

    /**
     * This method send a object to the client
     *
     * @param socket
     * @param object sent to the server
     * @throws IOException
     * @author Pierantonio Mauro
     */
    public static void sendData(Socket socket, Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(obj);
        out.flush();
    }

    /**
     * This method receive a object from the client
     *
     * @param socket
     * @return the object received
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Pierantonio Mauro
     */
    public static Object recieveData(Socket socket, Object obj) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return in.readObject();
    }
}
