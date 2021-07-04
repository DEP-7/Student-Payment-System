package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;
import service.UserServiceRedisImpl;
import service.exception.NotFoundException;
import util.MaterialUI;

public class SettingsFormController {
    public TextField txtUsername;
    public TextField txtCurrentPassword;
    public TextField txtNewPassword;
    public TextField txtConfirmPassword;

    UserServiceRedisImpl userService = new UserServiceRedisImpl();
    User loggedUser;

    public void initialize() {
        MaterialUI.paintTextFields(txtUsername, txtCurrentPassword, txtNewPassword, txtConfirmPassword);
        Platform.runLater(() -> loggedUser = (User) txtUsername.getScene().getWindow().getUserData());
    }

    public void btnUpdateUsername_OnAction(ActionEvent actionEvent) {
        updateLoginData(true, false);
    }

    public void btnUpdateUsername_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            updateLoginData(true, false);
        }
    }

    public void btnUpdatePassword_OnAction(ActionEvent actionEvent) {
        updateLoginData(false, true);
    }

    public void btnUpdatePassword_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            updateLoginData(false, true);
        }
    }

    private void updateLoginData(boolean updateUsername, boolean updatePassword) {
        trimTextFields(txtUsername, txtCurrentPassword, txtNewPassword, txtConfirmPassword);

        if (!isValidated(updateUsername, updatePassword)) {
            return;
        }

        if (updateUsername) {
            loggedUser.setUsername(txtUsername.getText());
            txtUsername.clear();
        }

        if (updatePassword) {
            loggedUser.setPassword(txtNewPassword.getText());
            txtCurrentPassword.clear();
            txtNewPassword.clear();
            txtConfirmPassword.clear();
        }

        String alertMessage = updateUsername && updatePassword ? "Login details updated successfully" : updateUsername ? "Username have been updated successfully" : "Password have been updated successfully";
        new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK).showAndWait();
        txtUsername.requestFocus();
    }

    private boolean isValidated(boolean checkUsername, boolean checkPassword) {

        if (checkUsername) {
            try {
                if (txtUsername.getText().isEmpty() || txtUsername.getText().length() < 4) {
                    new Alert(Alert.AlertType.ERROR, "Invalid username. Please use more than 4 letters", ButtonType.OK).show();
                    txtUsername.requestFocus();
                    return false;
                }

                userService.searchUser(txtUsername.getText());
                new Alert(Alert.AlertType.ERROR, "Username already exist", ButtonType.OK).show();
                txtUsername.requestFocus();
                return false;

            } catch (NotFoundException e) {
            }
        }

        if (checkPassword) {

            if (txtCurrentPassword.getText().isEmpty() || !loggedUser.isPasswordCorrect(txtCurrentPassword.getText())) {
                new Alert(Alert.AlertType.ERROR, "Current password is incorrect", ButtonType.OK).show();
                txtCurrentPassword.requestFocus();
                return false;
            } else if (txtNewPassword.getText().isEmpty() || txtNewPassword.getText().length() < 4) {
                new Alert(Alert.AlertType.ERROR, "Invalid password. Please use more than four characters", ButtonType.OK).show();
                txtNewPassword.requestFocus();
                return false;
            } else if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                new Alert(Alert.AlertType.ERROR, "Password didn't match. Try again", ButtonType.OK).show();
                txtConfirmPassword.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void trimTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }

    public void btnOk_OnAction(ActionEvent actionEvent) {
        ((Stage) txtUsername.getScene().getWindow()).close();
    }

    public void btnOk_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            ((Stage) txtUsername.getScene().getWindow()).close();
        }
    }
}
