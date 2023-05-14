package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class VirtualView implements View, Observer {

    private final ClientHandler clientHandler;

    /**
     * Constructor of the class
     */
    public VirtualView(ClientHandler clientHandler){ this.clientHandler = clientHandler; }

    @Override
    public void update(Message msg) {

    }

    @Override
    public void init() {

    }

    @Override
    public void asknickname() {

    }

    @Override
    public void askselecttile() {

    }

    @Override
    public void askswap() {

    }

    @Override
    public void askinsertcol() {

    }

    @Override
    public void boardshow(Space[][] board) {

    }

    @Override
    public void shelfshow(Optional<Tile>[][] shelf) {

    }

    @Override
    public void showtilesinhand(ArrayList<Tile> hand) {

    }

    @Override
    public void askplayernumber() {

    }

    @Override
    public void showpoints(Map<String, Integer> mappoints, Map<String, boolean[]> mapobjective) {

    }

    @Override
    public void showpublicobjective(String code) {

    }

    @Override
    public void showprivateobjective(PrivateObjective objective) {

    }
}
