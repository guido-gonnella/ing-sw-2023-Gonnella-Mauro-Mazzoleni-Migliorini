package it.polimi.ingsw.View.Gui.SceneControllers;

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

public class UsernameSceneController {
    public TextField UsernameBox;
    public javafx.scene.control.Button Button;
    private Stage stage;
    public boolean loginAttempt;

    public void login(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        notifyObservers(obs->obs.onNicknameUpdate(UsernameBox.getText()));
        loginAttempt = true;
    }

    public void maxPlayerScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/MaxPlayerScene.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.getScene().setRoot(root);
        stage.show();
    }

    public void waitingRoomScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/WaitingRoomScene.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.getScene().setRoot(root);
        stage.show();
    }
}
