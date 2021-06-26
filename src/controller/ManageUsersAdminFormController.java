package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import util.MaterialUI;

public class ManageUsersAdminFormController {
    public TableView<String> tblResult;
    public JFXCheckBox chkMakeAdmin;
    public ToggleGroup rbnGender;
    public TextField txtNameWithInitials;
    public TextField txtContactNumber;
    public TextField txtDateOfBirth;
    public TextField txtFullName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtEmail;
    public TextField txtNIC;
    public TextField txtAge;
    public JFXButton btnResetPassword;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public Label lblDefaultUserName;
    public Label lblDefaultPassword;

    public void initialize(){
        MaterialUI.paintTextFields(txtNameWithInitials,txtContactNumber,txtDateOfBirth,txtFullName,txtAddress,txtSearch,txtEmail,txtNIC,txtAge);
    }

    public void btnResetPassword_OnAction(ActionEvent actionEvent) {
    }

    public void btnResetPassword_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnEdit_OnAction(ActionEvent actionEvent) {
    }

    public void btnEdit_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
    }
}
