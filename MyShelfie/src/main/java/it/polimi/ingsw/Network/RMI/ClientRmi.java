package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.ClientPack.ClientConnection;
import it.polimi.ingsw.Network.Message.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRmi extends ClientConnection {

    private MyRemoteInterface client;
    private MyRemoteInterface server;
    private Registry registry;
    private String username;
    public ClientRmi(String address){
        try {
            client = new RmiServant();
            registry = LocateRegistry.getRegistry();
            server = (MyRemoteInterface) Naming.lookup("rmi://" + address + "/RmiConnection");
            server.setLastRemoteInterface(client);
        }catch (RemoteException | NotBoundException | MalformedURLException e){
            e.printStackTrace();
        }
    }
    @Override
    public synchronized Message readMessage() {
        try {
            Message msg;
            do{
                msg = client.clientReadMessage(username);
            }while(msg == null);
            return msg;
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void sendMessage(Message message) {
        try{
            server.clientSendMessage(username, message);
        }catch (RemoteException e){
            e.printStackTrace();
            //invia messaggio di errore
        }
    }

    @Override
    public void disconnect() {

    }
}
