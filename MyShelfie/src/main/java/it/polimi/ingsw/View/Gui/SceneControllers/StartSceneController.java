package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Observer.ViewObservable;
import it.polimi.ingsw.View.Gui.Gui2;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartSceneController extends ViewObservable implements GenericSceneController {
    @FXML
    private Button Button;

    @FXML
    public void initialize() {
        Button.setOnAction(this::initButton);
    }

    private void initButton(Event event) {
        Gui2.initialize(((Node) event.getSource()).getScene(),(Stage) (((Node) event.getSource()).getScene()).getWindow());
    }
}
