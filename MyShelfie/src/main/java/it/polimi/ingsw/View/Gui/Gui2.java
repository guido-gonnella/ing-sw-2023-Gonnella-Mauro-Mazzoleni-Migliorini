package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.Gui.SceneControllers.GenericSceneController;
import it.polimi.ingsw.View.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Gui2 extends ViewObservable implements View {

    private static Scene activeScene;
    private static Stage activeStage;
    private static GenericSceneController activeController;
    @Override
    public void init() {

    }

    @Override
    public void askNickname() {
        Platform.runLater(()-> planeLoader("UsernameScene.fxml"));
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
        Platform.runLater(()-> planeLoader("MaxPlayerScene.fxml"));
    }

    @Override
    public void showPoints(Map<String, Integer> mapPoints, Map<String, boolean[]> mapObjective) {

    }

    @Override
    public void showPublicObjective(PubObjType code) {
        Platform.runLater(()-> planeLoader("MainScene.fxml"));
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

    public static void planeLoader(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Gui2.class.getResource("/fxmls/"+ fxml));
        Parent root;
        try {
             root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        activeController= loader.getController();
        activeScene.setRoot(root);
        //activeStage.setScene(activeScene);
    }

    public static void initialize(Scene scene, Stage stage) {
        activeScene= scene;
        activeStage=stage;
        Platform.runLater(() -> planeLoader("MenuScene.fxml"));
    }

}
