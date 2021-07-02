package controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenFormController {
    public JFXProgressBar pgbLoading;
    public Label lblLoading;

    public void initialize() {
        pgbLoading.setProgress(0);

        Timeline timer = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (pgbLoading.getProgress() <= 1) {
                pgbLoading.setProgress(pgbLoading.getProgress() + 0.01);
            }
        }));
        timer.setRate(3);
        timer.setCycleCount(Animation.INDEFINITE);
        timer.playFromStart();

        pgbLoading.progressProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.intValue() == 1) {
                    timer.stop();
                    initializeUI();
                    ((Stage) pgbLoading.getScene().getWindow()).close();
                } else if (newValue.doubleValue() >= 0.95) {
                    spinUpRedisServerInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load the app, please contact the developer").showAndWait();
                System.exit(1);
            }
        });
    }

    private void initializeUI() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void spinUpRedisServerInstance() throws Exception {
        String[] commands = {"redis-server", "redis.conf", "--requirepass", "redis"};

        Process redisServer = Runtime.getRuntime().exec(commands);
        int exitCode = redisServer.waitFor();

        if (exitCode != 0) {
            throw new Exception("Failed to spin up the redis server instance");
        }
    }
}
