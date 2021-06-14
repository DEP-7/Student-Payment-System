package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.MaterialUI;

public class ManageStudentsFormController {
    public TextField txtNIC;
    public ToggleGroup rbtnGender;
    public TextField txtFullName;
    public TextField txtNameWithInitials;
    public TextField txtBirthday;
    public TextField txtHighestEducationalQualification;
    public TextField txtPreviousRegisteredCourses;
    public TextField txtAddress;
    public TextField txtContactNumber;
    public TextField txtEmail;
    public TextField txtAge;
    public TextField txtSearch;
    public JFXComboBox cmbCourseId;
    public JFXComboBox cmbBatchNumber;

    public void initialize(){
        MaterialUI.paintTextFields(txtNIC, txtFullName, txtNameWithInitials, txtBirthday, txtHighestEducationalQualification, txtPreviousRegisteredCourses, txtAddress, txtContactNumber, txtEmail, txtAge, txtSearch, cmbCourseId, cmbBatchNumber);
    }
}
