import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.JedisClient;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            JedisClient.getInstance().getClient().shutdown();
        }));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something went terribly wrong, try restarting the app").showAndWait();
            System.exit(1);
        });

        AnchorPane root = FXMLLoader.load(this.getClass().getResource("view/SplashScreenForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
