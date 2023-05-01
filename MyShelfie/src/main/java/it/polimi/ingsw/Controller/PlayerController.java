package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;

public class PlayerController {

    private Player player;
    private String nickaname;

    public PlayerController(Player player, String nickaname){
        this.player = player;
        this.nickaname = nickaname;
    }
}
