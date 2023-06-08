package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
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
    public void askPlayerNumber() {

    }

    @Override
    public void showPoints(Map<String, Integer> mappoints, Map<String, boolean[]> mapobjective) {

    }

    @Override
    public void showPublicObjective(PubObjType code) {

    }

    @Override
    public void showPrivateObjective(PrivateObjective objective) {

    }

    @Override
    public void invalidTile(int x, int y) {

    }

    @Override
    public void invalidCombo() {
    }

    @Override
    public void invalidColumn(int column) {

    }

    @Override
    public void showText(String text) {

    }

    @Override
    public void askSelection(Board board, Shelf shelf) {

    }
}
