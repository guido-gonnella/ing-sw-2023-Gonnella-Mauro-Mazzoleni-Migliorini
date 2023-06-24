package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.Message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {
    Message clientReadMessage(String user) throws RemoteException;
    void clientSendMessage(String user, Message message) throws RemoteException;
    Message serverReadMessage(String user) throws RemoteException;
    void serverSendMessage(Message message) throws RemoteException;
    void disconnect() throws RemoteException;
    void addUser(String user, MyRemoteInterface remoteInterface) throws RemoteException;
    MyRemoteInterface getLastInterface() throws RemoteException;
    MyRemoteInterface peekLastInterface() throws RemoteException;
    void setLastRemoteInterface(MyRemoteInterface remoteInterface) throws RemoteException;
}
