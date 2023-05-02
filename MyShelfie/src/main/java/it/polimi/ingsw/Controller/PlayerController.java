package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;

import java.net.Socket;

public class PlayerController {

    private Player player;
    private String nickaname;

    public PlayerController(Player player, String nickaname){
        this.player = player;
        this.nickaname = nickaname;
    }

    /*public static Socket connectionToServer(String address, int port){
        return
    }*/

}
