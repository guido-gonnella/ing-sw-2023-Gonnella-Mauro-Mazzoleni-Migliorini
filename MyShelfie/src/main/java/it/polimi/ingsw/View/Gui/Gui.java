package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Model.SerializableOptional;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.View;
import java.util.ArrayList;
import java.util.Map;

public class Gui extends ViewObservable implements View {


    @Override
    public void init() {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askSelectTile() {

    }


    @Override
    public void askInsertCol() {

    }

    @Override
    public void boardShow(Space[][] board) {

    }

    @Override
    public void shelfShow(SerializableOptional<Tile>[][] shelf) {

    }

    @Override
    public void showTilesInHand(ArrayList<Tile> hand) {

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
