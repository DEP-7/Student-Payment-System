package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.MaterialUI;

public class ViewBatchesFormController {
    public TextField txtSearch;
    public Label lblDelayedPayments;
    public Label lblNextPaymentDate;
    public JFXComboBox cmbCourseId;
    public Label lblBatchNumber;
    public Label lblNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, lblDelayedPayments, lblNextPaymentDate, cmbCourseId, lblBatchNumber, lblNotes);
    }
}
