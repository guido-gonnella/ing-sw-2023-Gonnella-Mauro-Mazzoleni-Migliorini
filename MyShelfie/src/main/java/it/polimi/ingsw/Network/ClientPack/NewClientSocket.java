package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.ErrorMessage;
import it.polimi.ingsw.Network.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used to communicate with the server, is purpose is to act as a blackbox
 * for the NetworkHandler which receives and sends messages
 */
public class NewClientSocket {
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public NewClientSocket(String address, int port) throws IOException {
        this.socket = new Socket(address, port);

        //sets a timeout for the socket on the client
        socket.setSoTimeout(10000);

        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());

    }

    /**
     * Read a message from the server and returns it to the NetworkHandler
     * @return the message received
     */
    public Message readMessage(){

        try{
            Message messageArrived = (Message) input.readObject();
            if(messageArrived != null) {
                return messageArrived;
            }
        }catch(IOException | ClassNotFoundException e){
            disconnect();
            return new ErrorMessage("SERVER", "Error in receiving the message");
        }
        //serve altrimenti il metodo si lamenta che manca il ritorno
        return null;
    }

    /**
     * Send a message to the server, if there is an exception the networkHandler will inform the player,
     * disconnect the client and end the game
     * @param message sent to the server
     */
    public void sendMessage(Message message) throws IOException{
        output.writeObject(message);
    }

    /**
     * Disconnect the socket from the server
     */
    public void disconnect(){
        try {
            output.close();
            input.close();
            if (socket != null && !socket.isClosed())
                socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /*public ArrayList<Tile> selectOrderTile(ArrayList<Tile> tiles){
        ArrayList<Tile> temp;
        for(int i=0; i<tiles.size(); i++){
        //printa le tiles in ordine
        //printa di selezionare un numero tra 1 e tiles.size(), ripeti fino a quando non è valido
        //aggiungi tile
        }
    }*/
}
