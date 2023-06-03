package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.C2S.NumberOfPlayerMessage;
import it.polimi.ingsw.Network.Message.C2S.UpdatePlInfoMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.S2C.AskNicknameMessage;
import it.polimi.ingsw.Network.Message.S2C.MaxNumPlayerRequestMsg;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Server implements Runnable {

    private Set<String> usernames;
    private ServerSocket ss;
    private Set<GameController> gameControllerSet;
    private VirtualView tempVirtualView;
    private final Object lock;
    private int maxPlayer;
    private int numPlayer;
    private boolean socketConnection;

    public Server(int port, boolean socket){
        usernames = new HashSet<>();
        tempVirtualView = new VirtualView(this);
        gameControllerSet = new HashSet<>();
        numPlayer = 0;
        maxPlayer = 0;
        lock = new Object();
        socketConnection = socket;
        if(socket){
            try {
                ss = new ServerSocket(port);
                System.out.println("Socket server: ON, port: " + port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //rmi
        }
    }


    /**
     * Main method of the server, accepts the clients and send them to a game already created,
     * otherwise creates a new game
     */
    @Override
    public void run(){

        while(!Thread.currentThread().isInterrupted()){

            SocketServer socketServer;
                socketServer = new SocketServer(ss);


            Message arrivedMessage;
            String username;
            do {
                do {
                    socketServer.sendMessage(new AskNicknameMessage());
                    arrivedMessage = socketServer.readMessage();
                } while (arrivedMessage.getMsgType() != MsgType.SEND_NICKNAME);

                username = ((UpdatePlInfoMessage) arrivedMessage).getNickname();
            }while(usernames.contains(username));
            usernames.add(username);

            synchronized (lock) {
                if (maxPlayer == 0) {
                    int tempNumPl;
                    do {
                        do {
                            socketServer.sendMessage(new MaxNumPlayerRequestMsg());
                            arrivedMessage = socketServer.readMessage();
                        } while (arrivedMessage.getMsgType() != MsgType.NUMBER_PLAYER_REPLY);
                        tempNumPl = ((NumberOfPlayerMessage) arrivedMessage).getNum();
                    } while (tempNumPl < 2 || tempNumPl > 4);
                    maxPlayer = tempNumPl;
                    numPlayer = 1;
                    tempVirtualView.addClient(username, socketServer);
                } else {
                    tempVirtualView.addClient(username, socketServer);
                    numPlayer++;
                    if (numPlayer == maxPlayer) {
                        numPlayer = 0;
                        maxPlayer = 0;
                        GameController gc = new GameController(tempVirtualView);
                        Thread thread = new Thread(gc, "gameController_");
                        thread.start();
                        gameControllerSet.add(gc);
                        tempVirtualView = new VirtualView(this);
                    }
                }
            }


        }
    }

    public void removeUsername(String username){
        usernames.remove(username);
    }

}
