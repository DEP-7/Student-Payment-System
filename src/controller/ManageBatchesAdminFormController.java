package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.MaterialUI;

public class ManageBatchesAdminFormController {
    public JFXComboBox cmbCourseId;
    public TextField txtSearch;
    public TextField txtBatchNumber;
    public TextField txtCourseStartingDate;
    public TextField txtCourseFinishedDate;
    public TextArea txtNotes;
    public Label lblNectPaymentDate;
    public Label lblDelayedPayments;

    public void initialize(){
        MaterialUI.paintTextFields(cmbCourseId,txtSearch,txtBatchNumber,txtCourseStartingDate,txtCourseFinishedDate,txtNotes,lblNectPaymentDate,lblDelayedPayments);
    }
}
