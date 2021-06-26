package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Batch;
import model.BatchTM;
import model.Course;
import service.BatchService;
import service.CourseService;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ManageBatchesAdminFormController {
    private final CourseService courseService = new CourseService();
    private final BatchService batchService = new BatchService();
    public JFXComboBox<String> cmbCourseId;
    public TableView<BatchTM> tblResult;
    public JFXCheckBox chkOngoingBatches;
    public TextField txtCourseStartingDate;
    public TextField txtCourseFinishedDate;
    public TextField txtBatchNumber;
    public TextField txtSearch;
    public JFXButton btnEndCourse;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public TextArea txtNotes;
    public Label lblNextPaymentDate;
    public Label lblDelayedPayments;

    public void initialize() {
        MaterialUI.paintTextFields(cmbCourseId, txtSearch, txtBatchNumber, txtCourseStartingDate, txtCourseFinishedDate, txtNotes, lblNextPaymentDate, lblDelayedPayments);

        for (Course course : courseService.searchAllCourses()) {
            cmbCourseId.getItems().add(course.getCourseID());
        }

        btnAdd.getParent().parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    MainFormController ctrl = (MainFormController) btnAdd.getScene().getUserData();
                    boolean[] formUpdateController = (boolean[]) ctrl.pneItemContainer.getUserData();
                    if (formUpdateController[3]) {
                        cmbCourseId.getItems().clear();
                        for (Course course : courseService.searchAllCourses()) {
                            cmbCourseId.getItems().add(course.getCourseID());
                        }
                        txtBatchNumber.clear();
                    }
                });
            }
        });

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("batchNumber"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("numberOfStudents"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("startedDate"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("planedEndDate"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("endDate"));

        cmbCourseId.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.isEmpty()) {
                txtBatchNumber.clear();
                return;
            }

            loadAllBatches(txtSearch.getText(), chkOngoingBatches.isSelected());

            try {
                txtBatchNumber.setText((batchService.getLastBatchNumber(courseService.searchCourse(cmbCourseId.getValue())) + 1) + "");
            } catch (NotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC\nError code batch 001").show();
                cmbCourseId.requestFocus();
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllBatches(newValue, chkOngoingBatches.isSelected());
        });

        chkOngoingBatches.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loadAllBatches(txtSearch.getText(), chkOngoingBatches.isSelected());
        });

        Platform.runLater(() -> loadAllBatches("", false));
    }

    private void loadAllBatches(String keyword, boolean onGoingBatchesOnly) {
        tblResult.getItems().clear();

        if (cmbCourseId.getValue() != null && !cmbCourseId.getValue().isEmpty()) {
            try {
                for (Batch batch : batchService.searchBatchByKeyword(courseService.searchCourse(cmbCourseId.getValue()), keyword, onGoingBatchesOnly)) {
                    tblResult.getItems().add(new BatchTM(batch.getBatchNumber(), 0/*TODO: Update number of students here*/, batch.getStartedDate(), batch.getStartedDate().plusDays(Integer.parseInt(batch.getCourse().getDuration().split(" - ")[0])), batch.getEndDate()));
                }
            } catch (NotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC\nError code batch 002").show();
                cmbCourseId.requestFocus();
            }
        }

    }

    public void btnEndCourse_OnAction(ActionEvent actionEvent) {
        if (txtCourseFinishedDate.getText().equals("Click button to end  -->>")) {
            txtCourseFinishedDate.setText(LocalDate.now().toString());
        } else {
            txtCourseFinishedDate.clear();
        }
    }

    public void btnEndCourse_OnKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            if (txtCourseFinishedDate.getText().equals("Click button to end  -->>")) {
                txtCourseFinishedDate.setText(LocalDate.now().toString());
            } else {
                txtCourseFinishedDate.clear();
            }
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        addBatch(false);
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addBatch(false);
        }
    }

    private void addBatch(boolean updateBatch) {
        trimTextFields(txtCourseStartingDate, txtBatchNumber, txtNotes);

        if (!isValidated()) {
            return;
        }

        try {
            Batch batch = new Batch(Integer.parseInt(txtBatchNumber.getText()),
                    courseService.searchCourse(cmbCourseId.getValue()),
                    LocalDate.parse(txtCourseStartingDate.getText()),
                    txtCourseFinishedDate.getText().isEmpty() ? null : LocalDate.parse(txtCourseFinishedDate.getText()),
                    txtNotes.getText());


            if (updateBatch) {
                batchService.updateBatch(batch);
            } else {
                batchService.addBatch(batch);
            }
            MainFormController ctrl = (MainFormController) cmbCourseId.getScene().getUserData();
            boolean[] formUpdatingController = (boolean[]) ctrl.pneItemContainer.getUserData();
            formUpdatingController[3] = true;
            formUpdatingController[2] = true;
            ctrl.pneItemContainer.setUserData(formUpdatingController);
            String alertMessage = updateBatch ? "Batch have been updated successfully" : "Batch have been added successfully";
            new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK).show();
            clearAll();
            loadAllBatches(txtSearch.getText(), chkOngoingBatches.isSelected());
            cmbCourseId.requestFocus();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Course already exist").show();
            cmbCourseId.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC\nError code batch 003").show();
            cmbCourseId.requestFocus();
        }
    }

    private void clearAll() {
        cmbCourseId.getSelectionModel().clearSelection();

        txtCourseStartingDate.clear();
        txtCourseFinishedDate.clear();
        txtBatchNumber.clear();
        txtNotes.clear();

        lblNextPaymentDate.setText("");
        lblDelayedPayments.setText("");

        setDisableAll(false);
    }

    private void setDisableAll(boolean value) {
        cmbCourseId.setDisable(value);
        txtCourseStartingDate.setDisable(value);
        txtBatchNumber.setDisable(value);
        txtNotes.setDisable(value);

        btnUpdate.setDisable(value);
        btnEdit.setDisable(!value);
        btnAdd.setDisable(value);
    }

    private boolean isValidated() {

        if (cmbCourseId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a course", ButtonType.OK).show();
            cmbCourseId.requestFocus();
            return false;
        }

        try {
            LocalDate.parse(txtCourseStartingDate.getText());
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid course starting date", ButtonType.OK).show();
            txtCourseStartingDate.requestFocus();
            return false;
        }

        return true;
    }

    private void trimTextFields(TextInputControl... textFields) {
        for (TextInputControl textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }

    public void btnEdit_OnAction(ActionEvent actionEvent) {
        setDisableAll(false);
    }

    public void btnEdit_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            setDisableAll(false);
        }
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        addBatch(true);
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addBatch(true);
        }
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
        clearAll();
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            clearAll();
        }
    }
}
