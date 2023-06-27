package it.polimi.ingsw.View.Gui;
import it.polimi.ingsw.Controller.NetworkHandlerTaskqueue;
import it.polimi.ingsw.View.Gui.SceneControllers.StartSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Main JavaFX class which starts the main stage and scene.
 */
public class JavaFXGui extends Application {

    @Override
    public void start(Stage stage) {
        Gui2 view = new Gui2();
        NetworkHandlerTaskqueue networkHandler = new NetworkHandlerTaskqueue(view, true);
        view.addObserver(networkHandler);
        Thread thread= new Thread(networkHandler, "networkHandler_");
        thread.start();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/StartScene.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        StartSceneController controller = loader.getController();
        //controller.addObserver(networkHandler);

        // Show the scene containing the root layout.
        Scene scene = new Scene(root);

        stage.setFullScreen(true);
        stage.setTitle("MyShelfie");
        stage.setFullScreenExitHint("");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Publisher material/Icon 50x50px.png"))));

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
