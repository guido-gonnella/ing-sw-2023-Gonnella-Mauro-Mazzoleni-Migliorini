package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.Gui.SceneControllers.MaxPlayerSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.MenuSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.UsernameSceneController;
import it.polimi.ingsw.View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Gui extends ViewObservable implements View {


    @Override
    public void init() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/MenuScene.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MenuSceneController controller = loader.getController();

        while(!controller.loginAttempt);
    }

    @Override
    public void askNickname() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/UsernameScene.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernameSceneController controller = loader.getController();

        while(!controller.loginAttempt);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/MaxPlayerScene.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MaxPlayerSceneController controller = loader.getController();

    }

    @Override
    public void showPoints(Map<String, Integer> mapPoints, Map<String, boolean[]> mapObjective) {

    }

    @Override
    public void showPublicObjective(PubObjType code) {

    }

    @Override
    public void showPrivateObjective(PrivateObjective objective) {

    }

    @Override
    public void invalidTile(int ROW, int COL) {

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
