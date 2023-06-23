package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Controller.NetworkHandler;
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

public class MenuSceneController {
    public TextField IpAddressBox;
    public TextField PortBox;
    public Button Button;
    public String ipAddress;
    public String portAddress;
    private Stage stage;
    public boolean loginAttempt;

    public void login(ActionEvent actionEvent) {
        ipAddress = IpAddressBox.getText();
        portAddress = PortBox.getText();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        boolean valid;
        if(ipAddress.equals("")){
            valid = false;
        }
        else valid = NetworkHandler.isValidIpAddress(ipAddress);
        if(valid) valid = NetworkHandler.isValidPort(Integer.parseInt(portAddress));
        if(!valid) {
            try {
                invalidPortOrIp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            String finalServerAddr = ipAddress;
            int finalPort = Integer.parseInt(portAddress);
            notifyObservers(obs -> obs.onConnection(finalServerAddr, finalPort));
            usernameScene();
        }
        loginAttempt = valid;
    }

    public void invalidPortOrIp() throws IOException {
        IpAddressBox.clear();
        PortBox.clear();
    }

    public void usernameScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/UsernameScene.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.getScene().setRoot(root);
        stage.show();
    }
}
