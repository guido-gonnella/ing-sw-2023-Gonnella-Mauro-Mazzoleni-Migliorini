package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.Message.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RmiServant extends UnicastRemoteObject implements MyRemoteInterface {

    private Registry registry;
    private MyRemoteInterface lastInterface = null;
    private final Map<String, MyRemoteInterface> remoteInterfaceMap;
    private final Map<String, Message> messageFromServer;
    private final Map<String, Message> messageFromClients;
    private Message msgFromServer = null;

    public RmiServant() throws RemoteException {
        remoteInterfaceMap = new HashMap<>();
        messageFromClients = new HashMap<>();
        messageFromServer = new HashMap<>();
    }

    //DONE
    @Override
    public Message clientReadMessage(String user) {
        return messageFromServer.remove(user);
    }

    @Override
    public void clientSendMessage(String user, Message message) throws RemoteException{
        messageFromClients.put(user, message);
    }

    //DONE
    @Override
    public Message serverReadMessage(String user) throws RemoteException {
        return messageFromClients.remove(user);
    }

    //DONE
    @Override
    public void serverSendMessage(Message message) throws RemoteException {
        msgFromServer = message;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void addUser(String user, MyRemoteInterface remoteInterface) throws RemoteException {
        this.remoteInterfaceMap.put(user, remoteInterface);
    }

    @Override
    public MyRemoteInterface getLastInterface() throws RemoteException {
        MyRemoteInterface temp = lastInterface;
        lastInterface = null;
        return temp;
    }

    @Override
    public MyRemoteInterface peekLastInterface() throws RemoteException {
        return lastInterface;
    }

    @Override
    public void setLastRemoteInterface(MyRemoteInterface remoteInterface){
        this.lastInterface = remoteInterface;
    }
}
