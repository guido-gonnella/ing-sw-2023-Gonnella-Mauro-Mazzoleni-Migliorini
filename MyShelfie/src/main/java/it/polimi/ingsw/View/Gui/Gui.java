package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.View;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Gui extends ViewObservable implements View {


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

    @Override
    public void invalidTile(int x, int y) {

    }

    @Override
    public void invalidcombo() {
    }

    @Override
    public void invalidColumn(int column) {

    }
}
