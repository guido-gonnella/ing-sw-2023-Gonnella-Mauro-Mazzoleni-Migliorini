package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.Gui.Gui2;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UsernameSceneController extends ViewObservable implements GenericSceneController {
    @FXML
    private TextField UsernameBox;
    @FXML
    private javafx.scene.control.Button Button;

    @FXML
    public void initialize() {
        Button.setOnAction(this::nickButton);
    }

    private void nickButton(Event event) {
        new Thread(() -> notifyObservers(obs -> obs.onNicknameUpdate(UsernameBox.getText()))).start();

        Platform.runLater(() -> Gui2.planeLoader("MainScene.fxml"));
    }
}