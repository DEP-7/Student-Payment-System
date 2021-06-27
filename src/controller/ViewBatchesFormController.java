package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Batch;
import model.BatchTM;
import model.Course;
import service.BatchService;
import service.CourseService;
import service.exception.NotFoundException;
import util.MaterialUI;
import util.ValidationUtil;

import java.time.LocalDate;

public class ViewBatchesFormController {
    private final CourseService courseService = new CourseService();
    private final BatchService batchService = new BatchService();
    public JFXComboBox<String> cmbCourseId;
    public TableView<BatchTM> tblResult;
    public JFXCheckBox chkOngoingBatches;
    public TextField txtSearch;
    public Label lblDelayedPayments;
    public Label lblNextPaymentDate;
    public Label lblBatchNumber;
    public Label lblNotes;

    /*0 - Dashboard
     * 1 - Add New Payment
     * 2 - View Payments
     * 3 - Manage Students
     * 4 - View Courses
     * 5 - View Batches
     * 6 - Manage Batches
     * 7 - Manage Courses
     * 8 - Manage Users*/

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, lblDelayedPayments, lblNextPaymentDate, cmbCourseId, lblBatchNumber, lblNotes);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("batchNumber"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("numberOfStudents"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("startedDate"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("planedEndDate"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("endDate"));

        for (Course course : courseService.searchAllCourses()) {
            cmbCourseId.getItems().add(course.getCourseID());
        }

        tblResult.getParent().parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    MainFormController ctrl = (MainFormController) tblResult.getScene().getUserData();
                    boolean[] formUpdateController = (boolean[]) ctrl.pneItemContainer.getUserData();
                    if (formUpdateController[5]) {
                        cmbCourseId.getItems().clear();
                        for (Course course : courseService.searchAllCourses()) {
                            cmbCourseId.getItems().add(course.getCourseID());
                        }
                    }
                });
            }
        });

        cmbCourseId.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.isEmpty()) {
                loadAllBatches(txtSearch.getText(), chkOngoingBatches.isSelected());
            }

        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllBatches(newValue, chkOngoingBatches.isSelected());
        });

        chkOngoingBatches.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loadAllBatches(txtSearch.getText(), chkOngoingBatches.isSelected());
        });

        tblResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {// TODO: Need to check this method
                lblBatchNumber.setText(newValue.getBatchNumber() + "");
//                lblDelayedPayments.setText(); TODO: Update this

                try {
                    Course course = newValue.getCourse();
                    Batch batch = batchService.searchBatch(newValue.getBatchNumber(), course);
                    lblNotes.setText(batch.getNotes());

                    LocalDate batchStartedDate = batch.getStartedDate();
                    String[] firstInstallment = course.getFirstInstallment().split(" - ");
                    String[] installmentGap = course.getInstallmentGap().split(" - ");
                    int numberOfInstallments = course.getNumberOfInstallments();

                    LocalDate paymentDate = batchStartedDate.plusDays(Long.parseLong(firstInstallment[0]) * (firstInstallment[1].equals("Weeks") ? 7 : 1));
                    if (ValidationUtil.isValidPastDate(paymentDate.toString())) {
                        for (int i = 0; i < numberOfInstallments - 1; i++) {
                            paymentDate = installmentGap[1].equals("Months") ? batchStartedDate.plusMonths(Long.parseLong(firstInstallment[0])) : batchStartedDate.plusDays(Long.parseLong(firstInstallment[0]));
                            if (!ValidationUtil.isValidPastDate(paymentDate.toString())) {
                                lblNextPaymentDate.setText(paymentDate.toString());
                                break;
                            }
                        }
                    } else {
                        lblNextPaymentDate.setText(paymentDate.toString());
                    }

                    if (lblNextPaymentDate.getText().equals("Select a batch from table to view details")) {
                        lblNextPaymentDate.setText("All payment dates already passed");
                    }

                } catch (NotFoundException e) {
                    e.printStackTrace();
                }

            }else{
                lblDelayedPayments.setText("Select a batch from table to view details");
                lblNextPaymentDate.setText("Select a batch from table to view details");
                lblBatchNumber.setText("Select a batch from table to view details");
                lblNotes.setText("Select a batch from table to view details");
            }
        });
    }

    private void loadAllBatches(String keyword, boolean onGoingBatchesOnly) {
        tblResult.getItems().clear();

        if (cmbCourseId.getValue() != null && !cmbCourseId.getValue().isEmpty()) {
            try {
                for (Batch batch : batchService.searchBatchByKeyword(courseService.searchCourse(cmbCourseId.getValue()), keyword, onGoingBatchesOnly)) {
                    tblResult.getItems().add(new BatchTM(batch.getBatchNumber(), 0/*TODO: Update number of students here*/, batch.getStartedDate(), batch.getStartedDate().plusDays(Integer.parseInt(batch.getCourse().getDuration().split(" - ")[0])), batch.getEndDate(), courseService.searchCourse(cmbCourseId.getValue())));
                }
            } catch (NotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC\nError code batch 002").show();
                cmbCourseId.requestFocus();
            }
        }

    }
}
