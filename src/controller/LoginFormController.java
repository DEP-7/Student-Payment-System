package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUsername;

    public void btnLogin_OnAction(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) txtUsername.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        MainFormController ctrl = fxmlLoader.getController();
        Scene mainScene = new Scene(root);
        mainScene.setUserData(ctrl);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Student Payment System");
        mainStage.setMaxWidth(1860);
        mainStage.setMinWidth(950);// Need to change this values
        mainStage.setMinHeight(800);// Need to change this values
        mainStage.setResizable(true);
        Platform.runLater(() -> {
            mainStage.setMaximized(true);
            mainStage.centerOnScreen();
            mainStage.sizeToScene();
        });
        Parent dashboardFormLoader = FXMLLoader.load(this.getClass().getResource("../view/DashboardForm.fxml"));
        ctrl.pneStage.getChildren().add(dashboardFormLoader);

        AnchorPane.setRightAnchor(dashboardFormLoader,0.0);
        AnchorPane.setLeftAnchor(dashboardFormLoader,0.0);
        AnchorPane.setTopAnchor(dashboardFormLoader,0.0);
        AnchorPane.setBottomAnchor(dashboardFormLoader,0.0);
    }
}
