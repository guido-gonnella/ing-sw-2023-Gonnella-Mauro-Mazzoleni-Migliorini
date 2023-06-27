package it.polimi.ingsw.View.Gui;
import it.polimi.ingsw.Controller.NetworkHandler;
import it.polimi.ingsw.Controller.NetworkHandlerTaskqueue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Main JavaFX class which starts the main stage and scene.
 */
public class JavaFXGui extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Gui2 view = new Gui2();
        NetworkHandlerTaskqueue networkHandler = new NetworkHandlerTaskqueue(view, true);
        view.addObserver(networkHandler);
        Thread thread= new Thread(networkHandler, "networkHandler_");
        thread.start();

        // Load root layout from fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/StartScene.fxml")));
        //    MenuSceneController controller = loader.getController();
        //  controller.addObserver(clientController);

        // Show the scene containing the root layout.
        Scene scene = new Scene(root);

  /*      stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);*/
        stage.setFullScreen(true);
        stage.setTitle("MyShelfie");
        stage.setFullScreenExitHint("");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Publisher material/Icon 50x50px.png"))));

        stage.setScene(scene);
        stage.show();
        view.initialize(scene,stage);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
