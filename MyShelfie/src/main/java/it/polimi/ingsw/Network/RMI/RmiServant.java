package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.Message.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServant extends UnicastRemoteObject implements MyRemoteInterface {

    private Registry registry;
    public RmiServant(int port) throws RemoteException {
        super();
        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //registry.rebind("ChatRMI", RmiServer);
    }
    @Override
    public Message readMessage() {
        return null;
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void disconnect() {

    }
}
