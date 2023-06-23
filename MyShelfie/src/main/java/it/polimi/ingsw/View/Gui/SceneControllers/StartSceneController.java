package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.View.Gui.Gui;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

public class StartSceneController {
    public void pressToContinue(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/MenuScene.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();

        Gui view = new Gui();
        NetworkHandler networkHandler = new NetworkHandler(view);
        view.addObserver(networkHandler);
        Thread thread= new Thread(networkHandler, "networkHandler_");
        thread.start();
    }
}
