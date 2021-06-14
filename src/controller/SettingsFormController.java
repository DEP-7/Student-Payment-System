package controller;

import javafx.scene.control.TextField;
import util.MaterialUI;

public class SettingsFormController {
    public TextField txtUsername;
    public TextField txtCurrentPassword;
    public TextField txtNewPassword;
    public TextField txtConfirmPassword;

    public void initialize() {
        MaterialUI.paintTextFields(txtUsername, txtCurrentPassword, txtNewPassword, txtConfirmPassword);
    }
}
