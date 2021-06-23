package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.CourseTM;
import util.MaterialUI;

public class ManageCoursesAdminFormController {
    public TableView<CourseTM> tblResult;
    public ToggleGroup rbnFirstInstallment;
    public ToggleGroup rbnInstallmentGap;
    public ToggleGroup rbnCourseStatus;
    public ToggleGroup rbnDuration;
    public TextField txtNumberOfInstallments;
    public TextField txtNumberOfStudents;
    public TextField txtFirstInstallment;
    public TextField txtCourseDuration;
    public TextField txtInstallmentGap;
    public TextField txtCourseName;
    public TextField txtCourseFee;
    public TextField txtCourseID;
    public TextField txtFileName;
    public TextField txtSearch;
    public JFXButton btnAddFile;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public TextArea txtMinimumRequirements;
    public TextArea txtNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, txtCourseName, txtCourseID, txtCourseFee, txtNumberOfInstallments, txtNumberOfStudents, txtCourseDuration, txtFirstInstallment, txtInstallmentGap, txtFileName, txtMinimumRequirements, txtNotes);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("courseID"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("courseName"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("courseFee"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("numberOfInstallments"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("duration"));
        tblResult.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("courseAvailable"));
        tblResult.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("courseInitiationDate"));
    }

    public void btnAddFile_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddFile_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
    }

    public void btnEdit_OnAction(ActionEvent actionEvent) {
    }

    public void btnEdit_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
    }
}
