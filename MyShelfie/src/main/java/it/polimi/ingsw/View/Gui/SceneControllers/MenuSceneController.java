package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MenuSceneController extends ViewObservable implements GenericSceneController {
    @FXML
    private TextField IpAddressBox;
    @FXML
    private TextField PortBox;
    @FXML
    private Button Button;

    @FXML
    public void initialize() {
        Button.setOnAction(this::serverInfoButton);
    }

    private void serverInfoButton(Event event) {
        String ipAddress = IpAddressBox.getText();
        int portAddress = Integer.parseInt(PortBox.getText());

        boolean valid;
        if(ipAddress.equals("")){
            valid = false;
        }
        else valid = NetworkHandler.isValidIpAddress(ipAddress);
        if(valid) valid = NetworkHandler.isValidPort(portAddress);
        if(!valid) {
            try {
                invalidPortOrIp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            new Thread(() -> notifyObservers(obs -> obs.onConnection(ipAddress, portAddress))).start();
        }
    }

    private void invalidPortOrIp() throws IOException {
        IpAddressBox.clear();
        PortBox.clear();
    }
}
