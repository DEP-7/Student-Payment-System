package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Batch;
import model.Course;
import service.BatchService;
import service.CourseService;
import service.exception.NotFoundException;
import util.MaterialUI;

public class ViewPaymentsFormController {
    public ToggleGroup rbtnDayGaps;
    public TextField txtFromDate;
    public TextField txtReceiptNumber;
    public TextField txtNIC;
    public JFXComboBox<String> cmbCourseId;
    public JFXComboBox<String> cmbBatchNumber;
    public TextField txtPaymentDescription;
    public TextField txtPaymentMethod;
    public TextField txtOnlineReference;
    public TextField txtCardNumber;
    public TextField txtExpirationDate;
    public TextField txtNameOnCard;
    public TextField txtTotalAmount;
    public TextField txtAmountReceived;
    public TextField txtBalanceAmount;
    public TextField txtDueDate;
    public TextField txtBalanceSettledDate;
    public TextField txtReceiptDate;
    public TextField txtCashier;
    public TextField txtNotes;
    public TextField txtSearch;
    public TextField txtToDate;
    public JFXButton btnAdd;
    CourseService courseService = new CourseService();
    BatchService batchService = new BatchService();

    public void initialize() {
        MaterialUI.paintTextFields(txtFromDate, txtReceiptNumber, txtNIC, cmbCourseId, cmbBatchNumber, txtPaymentDescription, txtPaymentMethod, txtOnlineReference, txtCardNumber, txtExpirationDate, txtNameOnCard, txtTotalAmount, txtAmountReceived, txtBalanceAmount, txtDueDate, txtBalanceSettledDate, txtReceiptDate, txtCashier, txtNotes, txtSearch, txtToDate);

        btnAdd.getParent().parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                MainFormController ctrl = (MainFormController) btnAdd.getScene().getUserData(); // TODO: Chance to NullPointerException if then see the same code in Manage students form
                boolean[] formUpdateController = (boolean[]) ctrl.pneItemContainer.getUserData();
                if (formUpdateController[2]) {
                    cmbCourseId.getItems().clear();
                    for (Course course : courseService.searchAllCourses()) {
                        cmbCourseId.getItems().add(course.getCourseID());
                    }
                    if (!cmbCourseId.getValue().isEmpty()) {
                        try {
                            cmbBatchNumber.getItems().clear();
                            for (Batch batch : batchService.searchAllBatches(courseService.searchCourse(cmbCourseId.getValue()))) {
                                cmbBatchNumber.getItems().add(batch.getBatchNumber() + "");
                            }
                        } catch (NotFoundException e) {
                            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
                            //TODO: Set focusable component here
                        }
                    }
                }
            }
        });

        cmbCourseId.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    cmbBatchNumber.getItems().clear();
                    for (Batch batch : batchService.searchAllBatches(courseService.searchCourse(newValue))) {
                        cmbBatchNumber.getItems().add(batch.getBatchNumber() + "");
                    }
                } catch (NotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC\nError code payments 001").show();
                    //TODO: Set focusable component here
                }
            }
        });
    }
}
