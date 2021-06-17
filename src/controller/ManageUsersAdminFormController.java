package controller;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.MaterialUI;

public class ManageUsersAdminFormController {
    public ToggleGroup rbnAvailability;
    public TextField txtNameWithInitials;
    public TextField txtContactNumber;
    public TextField txtDateOfBirth;
    public TextField txtFullName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtEmail;
    public TextField txtNIC;
    public TextField txtAge;

    public void initialize(){
        MaterialUI.paintTextFields(txtNameWithInitials,txtContactNumber,txtDateOfBirth,txtFullName,txtAddress,txtSearch,txtEmail,txtNIC,txtAge);
    }
}
