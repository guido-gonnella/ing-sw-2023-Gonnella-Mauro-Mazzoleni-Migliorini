package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.Gui.SceneControllers.GenericSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.MaxPlayerSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.MenuSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.UsernameSceneController;
import it.polimi.ingsw.View.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import java.io.PrintStream;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Gui2 extends ViewObservable implements View {

    private static Scene activeScene;
    private static Stage activeStage;
    private static GenericSceneController activeController;
    @Override
    public void init() {
        PrintStream out=new PrintStream(System.out);
        out.println("1");
    }

    @Override
    public void askNickname() {
        UsernameSceneController controller = new UsernameSceneController();
        Platform.runLater(()-> planeloader( controller,"UsernameScene.fxml"));


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
        MaxPlayerSceneController controller = new MaxPlayerSceneController();
        planeloader(controller,"MaxPlayerScene.fxml");
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
    public void planeloader(GenericSceneController controller, String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/"+ fxml));
        //FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxmls/MenuScene.fxml")));

        activeController = controller;
        Parent root;
        try {
             root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        activeScene.setRoot(root);
     //   activeStage.setScene(activeScene);

    }
    public  void initialize(Scene scene,Stage stage){
        activeScene= scene;
        activeStage=stage;
        MenuSceneController sceneController =new MenuSceneController();
        Platform.runLater(()->planeloader(sceneController,"MenuScene.fxml"));
    }

}
