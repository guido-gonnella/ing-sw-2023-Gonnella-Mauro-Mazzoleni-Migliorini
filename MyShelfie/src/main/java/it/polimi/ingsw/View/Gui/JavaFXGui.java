package it.polimi.ingsw.View.Gui;
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
        // Load root layout from fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmls/StartScene.fxml")));
        //    MenuSceneController controller = loader.getController();
        //  controller.addObserver(clientController);

        // Show the scene containing the root layout.
        Scene scene = new Scene(root);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("MyShelfie");
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
