package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.MaterialUI;

public class ManageBatchesAdminFormController {
    public JFXCheckBox chkOngoingBatches;
    public JFXComboBox cmbCourseId;
    public TextField txtCourseStartingDate;
    public TextField txtCourseFinishedDate;
    public TextField txtBatchNumber;
    public TextField txtSearch;
    public JFXButton btnEndCourse;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public TextArea txtNotes;
    public JFXButton btnAdd;
    public Label lblNextPaymentDate;
    public Label lblDelayedPayments;

    public void initialize(){
        MaterialUI.paintTextFields(cmbCourseId,txtSearch,txtBatchNumber,txtCourseStartingDate,txtCourseFinishedDate,txtNotes,lblNextPaymentDate,lblDelayedPayments);
    }

    public void btnEndCourse_OnAction(ActionEvent actionEvent) {
    }

    public void btnEndCourse_OnKeyPress(KeyEvent keyEvent) {
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

    public void btnClear_OnAction(ActionEvent actionEvent) {
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
    }
}
