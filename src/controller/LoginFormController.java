package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.exception.NotFoundException;

import java.io.IOException;

public class LoginFormController {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUsername;
    UserService userService = new UserService();

    public void btnLogin_OnAction(ActionEvent actionEvent) {
        loginIn();
    }

    public void btnLogin_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            loginIn();
        }
    }

    private void loginIn() {
        User loginUser;

        try {
            loginUser = userService.searchUserByUsername(txtUsername.getText().trim());
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            txtUsername.requestFocus();
            return;
        }

        if (txtPassword.getText().trim().isEmpty() || !loginUser.isPasswordCorrect(txtPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid password").show();
            txtPassword.requestFocus();
            return;
        }

        Stage mainStage = (Stage) txtUsername.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/MainForm.fxml"));

        try {
            Parent root = fxmlLoader.load();
            MainFormController ctrl = fxmlLoader.getController();
            Scene mainScene = new Scene(root);

            mainScene.setUserData(ctrl);
            mainStage.setUserData(loginUser);

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

            AnchorPane.setRightAnchor(dashboardFormLoader, 0.0);
            AnchorPane.setLeftAnchor(dashboardFormLoader, 0.0);
            AnchorPane.setTopAnchor(dashboardFormLoader, 0.0);
            AnchorPane.setBottomAnchor(dashboardFormLoader, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
        clearAll();
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            clearAll();
        }
    }

    private void clearAll() {
        txtUsername.clear();
        txtPassword.clear();
        txtUsername.requestFocus();
        // TODO: reset profile pic
    }

    public void lblForgottenPassword_OnAction(MouseEvent mouseEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Please contact an admin to reset the password").show();
        txtUsername.requestFocus();
    }
}
