package it.polimi.ingsw.View.Gui.SceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Scanner;

import static it.polimi.ingsw.Observer.ViewObservable.notifyObservers;

public class MaxPlayerSceneController {
    public TextField MaxPlayerBox;
    public javafx.scene.control.Button Button;
    private Stage stage;
    public boolean loginAttempt;

    public void login(ActionEvent actionEvent) {
        int playerNumber;
        boolean valid;

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        valid=true;
        playerNumber = Integer.parseInt(MaxPlayerBox.getText());

        if(playerNumber>4 || playerNumber<2) {
            valid=false;
        }
        else {
            final int maxPlayers = playerNumber;
            notifyObservers(obs -> obs.onPlayerNumberReply(maxPlayers));
        }
        loginAttempt = valid;
    }
}
