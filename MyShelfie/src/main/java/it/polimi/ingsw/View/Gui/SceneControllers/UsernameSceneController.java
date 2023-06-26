package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static it.polimi.ingsw.Observer.ViewObservable.notifyObservers;

public class UsernameSceneController extends ViewObservable implements GenericSceneController {
    public TextField UsernameBox;
    public javafx.scene.control.Button Button;
    private Stage stage;
    public boolean loginAttempt;

    public void login(ActionEvent actionEvent) {

        new Thread(() -> notifyObservers(obs -> obs.onNicknameUpdate(UsernameBox.getText()))).start();
    }

}