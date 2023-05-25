package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.C2S.NumberOfPlayerMessage;
import it.polimi.ingsw.Network.Message.C2S.UpdatePlInfoMessage;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Network.Message.S2C.AskNicknameMessage;
import it.polimi.ingsw.Network.Message.S2C.MaxNumPlayerRequestMsg;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Server implements Runnable {

    private int port;
    private Set<String> usernames;
    private ServerSocket ss;
    private Set<GameController> gameControllerSet;
    private VirtualView tempVirtualView;
    private final Object lock;
    private int maxPlayer;
    private int numPlayer;

    public Server(int port){
        this.port = port;
        usernames = new HashSet<>();
        tempVirtualView = new VirtualView();
        numPlayer = 0;
        maxPlayer = 0;
        lock = new Object();
        try{
            ss = new ServerSocket(port);
            System.out.println("Socket server: ON, port: " + port);
        }catch (IOException e){
            e.printStackTrace();
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
            try{
                socketServer = new SocketServer(ss);
            }catch (IOException e){
                e.printStackTrace();
                return;
            }

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

            if(maxPlayer == 0){
                do {
                    socketServer.sendMessage(new MaxNumPlayerRequestMsg());
                    arrivedMessage = socketServer.readMessage();
                } while (arrivedMessage.getMsgType() != MsgType.NUMBER_PLAYER_REPLY);
                maxPlayer = ((NumberOfPlayerMessage) arrivedMessage).getNum();
                numPlayer = 1;
                tempVirtualView.addClient(username, socketServer);
            }else{
               tempVirtualView.addClient(username, socketServer);
               numPlayer++;
               if(numPlayer == maxPlayer){
                   numPlayer = 0;
                   maxPlayer = 0;
                   GameController gc = new GameController(tempVirtualView);
                   Thread thread = new Thread(gc, "gameController_");
                   thread.start();
               }
            }


        }
    }

}
