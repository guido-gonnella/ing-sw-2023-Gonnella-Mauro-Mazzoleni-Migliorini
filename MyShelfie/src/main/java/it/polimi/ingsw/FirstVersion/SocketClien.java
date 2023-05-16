package it.polimi.ingsw.FirstVersion;

import it.polimi.ingsw.View.Cli;

import java.io.*;
import java.net.Socket;

public class SocketClien {

    private int port;
    private Cli cli;
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public SocketClien(String address, int port){
        try {
            this.socket = new Socket(address, port);
            in = null;
            out = null;
        }catch (IOException e){
            System.err.println("Error in creating client");
            System.exit(1);
        }
    }
/*
    public void receiveMessage(){
        try{
            in = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(in);
            MessagesAAA rec = (MessagesAAA) inputStream.readObject();
            inputStream.close();
            switch (rec){
                case GET_USERNAME -> sendUsername();
                case GET_NUM_PLAYERS -> sendNumPlayers();
            }
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error in receiving message");
            System.exit(1);
        }
    }

    public void sendUsername(){
        // AGGIUSTARE, EVENTUALMENTE CREARE UNA NUOVA CLI AD HOC
        String username = cli.asknickname();
        try {
            out = socket.getOutputStream();
            byte[] messageByte = username.getBytes();
            out.write(messageByte);
            out.flush();
        }catch(IOException e){
            System.err.println("Error sending string");
            System.exit(1);
        }
    }

    public void sendNumPlayers(){
        // AGGIUSTARE, EVENTUALMENTE CREARE UNA NUOVA CLI AD HOC
        int value = cli.askplayernumber();
        try {
            out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeInt(value);
            dataOutputStream.flush();
        }catch(IOException e){
            System.err.println("Error in sending int");
            System.exit(1);
        }
    }*/
}
