package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.SocketServer;
import it.polimi.ingsw.View.GameView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameController {

    private ArrayList<Player> players;
    private ArrayList<Socket> sockets;
    ServerSocket socketServer;
    public Game createGame(){
        this.players = new ArrayList<Player>();
        return new Game();
    }

    public void createServer() throws IOException {
        int port = GameView.takePort();
        this.socketServer = SocketServer.serverConnection(port);
    }

    public Player addPlayer(int port) throws IOException, ClassNotFoundException {
        Socket temp = socketServer.accept();
        String nick = String.valueOf(SocketServer.recieveData(temp, port));
        Player pl = new Player(nick);
        this.sockets.add(temp);
        this.players.add(pl);
        return pl;
    }




}
