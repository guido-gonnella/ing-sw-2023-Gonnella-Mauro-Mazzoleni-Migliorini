package it.polimi.ingsw.Network.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject {
    protected RmiServer() throws RemoteException {
    }
}
