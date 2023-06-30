package it.polimi.ingsw.View.Gui;


import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.Gui.SceneControllers.GenericSceneController;
import it.polimi.ingsw.View.Gui.SceneControllers.MainSceneController;
import it.polimi.ingsw.View.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Gui2 extends ViewObservable implements View {

    private static Scene activeScene;
    private static Stage activeStage;

    private static GenericSceneController activeController;
    private static boolean Turnfirst;
    private static boolean endofgame;
    @Override
    public void init() {
    }

    @Override
    public void askNickname() {
        Platform.runLater(()-> planeLoader("UsernameScene.fxml"));
    }

    @Override
    public void askSelectTile() {
        if (Turnfirst) {

            Platform.runLater(() -> ((MainSceneController) activeController).setText("It's your turn!\n"));
        }
        Platform.runLater(() -> ((MainSceneController) activeController).enableButtons());
        Turnfirst=false;
    }


    @Override
    public void askInsertCol() {
        Turnfirst=true;
        Platform.runLater(() -> ((MainSceneController) activeController).disableButtons());
        Platform.runLater(() -> ((MainSceneController) activeController).enableColSelection());
    }

    @Override
    public void boardShow(Space[][] board) {
        Platform.runLater(() -> ((MainSceneController) activeController).setBoard(board));
    }

    @Override
    public void shelfShow(SerializableOptional<Tile>[][] shelf) {
        Platform.runLater(() -> ((MainSceneController) activeController).disableColSelection());
        Platform.runLater(() -> ((MainSceneController) activeController).setShelf(shelf));
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
        endofgame=true;
        Platform.runLater(() -> ((MainSceneController) activeController).setFinalPoints(mapPoints, mapObjective));
    }

    @Override
    public void showPublicObjective(PubObjType code) {
        Platform.runLater(() -> ((MainSceneController) activeController).setPublicObjectives(code));
    }

    @Override
    public void showPrivateObjective(PrivateObjective objective) {
        Platform.runLater(() -> ((MainSceneController) activeController).setPrivateObjective(objective));
    }

    @Override
    public void invalidTile(int ROW, int COL) {

    }

    @Override
    public void invalidCombo() {
        Platform.runLater(() -> ((MainSceneController) activeController).setText("Invalid combo!\n"));
        Platform.runLater(() -> ((MainSceneController) activeController).disableButtons());
    }

    @Override
    public void invalidColumn(int column) {

    }

    @Override
    public void showText(String text) {
            if (endofgame){
                Platform.runLater(() -> ((MainSceneController) activeController).setText(text));
            }
    }

    public static void planeLoader(String fxml) {
        FXMLLoader loader = new FXMLLoader(Gui2.class.getResource("/fxmls/"+ fxml));
        Parent root;
        try {
             root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        activeController = loader.getController();
        activeScene.setRoot(root);
        //activeStage.setScene(activeScene);
    }

    public static void initialize(Scene scene, Stage stage) {
        activeScene = scene;
        activeStage = stage;
        Turnfirst=true;
        endofgame=false;
        Platform.runLater(() -> planeLoader("MenuScene.fxml"));
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    public static Stage getActiveStage() {
        return activeStage;
    }
}
