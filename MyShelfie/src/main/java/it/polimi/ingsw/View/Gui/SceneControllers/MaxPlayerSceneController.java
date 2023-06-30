package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.Gui.Gui2;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MaxPlayerSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private TextField MaxPlayerBox;
    @FXML
    private javafx.scene.control.Button Button;

    @FXML
    public void initialize() {
        Button.setOnAction(this::playersButton);
    }

    private void playersButton(Event event) {
        int playerNumber=0;
        boolean valid = false;

        while(!valid) {
            try {
                playerNumber = Integer.parseInt(MaxPlayerBox.getText());
            }catch(NumberFormatException e){
                valid=false;
                playerNumber=0;
            }
                if (playerNumber > 4 || playerNumber < 2) {
                    reset();
                } else {
                    valid = true;
                    final int maxPlayers = playerNumber;
                    new Thread(() -> notifyObservers(obs -> obs.onPlayerNumberReply(maxPlayers))).start();
                }

        }

        Platform.runLater(() -> Gui2.planeLoader("MainScene.fxml"));
    }

    private void reset() {
        MaxPlayerBox.clear();
    }
}
