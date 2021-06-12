package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUsername;

    public void btnLogin_OnAction(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) txtUsername.getScene().getWindow();
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Dashboard");
        mainStage.setResizable(true);

        Platform.runLater(() -> {
            mainStage.setMaximized(true);
            mainStage.centerOnScreen();
            mainStage.sizeToScene();
        });
    }
}
