import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("view/SplashScreenForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();


//        Parent root = FXMLLoader.load(this.getClass().getResource("view/LoginForm.fxml"));
////        Parent root = FXMLLoader.load(this.getClass().getResource("view/MainForm.fxml"));
//        Scene loginScene = new Scene(root);
//        primaryStage.setScene(loginScene);
//        primaryStage.setTitle("Login");
//        primaryStage.setResizable(false);
//        primaryStage.centerOnScreen();
//        primaryStage.show();
    }
}
