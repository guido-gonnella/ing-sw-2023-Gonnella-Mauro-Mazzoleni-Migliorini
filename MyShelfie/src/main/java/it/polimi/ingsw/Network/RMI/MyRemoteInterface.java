package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.Message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {
    public abstract Message readMessage() throws RemoteException;
    public abstract void sendMessage(Message message) throws RemoteException;
    public abstract void disconnect() throws RemoteException;
}
