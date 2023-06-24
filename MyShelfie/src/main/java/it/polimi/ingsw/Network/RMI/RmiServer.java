package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.ServerPack.ServerConnection;

import java.rmi.RemoteException;

public class RmiServer extends ServerConnection {

    private String username;
    private RmiServant server;
    private MyRemoteInterface client;

    public RmiServer(RmiServant servant) throws RemoteException {
        try{
            do{
                client = servant.peekLastInterface();
            }while(client == null);
            client = servant.getLastInterface();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public synchronized Message readMessage(){
        try{
            Message msg;
            do{
                msg = client.serverReadMessage(username);
            }while(msg == null);
            return msg;
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void sendMessage(Message message){
        try{
            client.serverSendMessage(message);
        }catch (RemoteException e){
            e.printStackTrace();
            //invia messaggio di errore
        }
    }

    public synchronized void disconnect(){

    }
}
