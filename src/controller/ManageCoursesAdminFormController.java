package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Course;
import model.CourseTM;
import service.CourseService;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCoursesAdminFormController {
    private final CourseService courseService = new CourseService();
    public TableView<CourseTM> tblResult;
    public ToggleGroup rbnFirstInstallment;
    public ToggleGroup rbnInstallmentGap;
    public ToggleGroup rbnCourseStatus;
    public ToggleGroup rbnDuration;
    public JFXCheckBox chkUnlimitedStudents;
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

    private boolean isFirstFocusLostFromCourseId = true;
    private String courseIdToUpdate = "";
    private ArrayList<Integer> caretPositionArray;
    private TextField previouslyFocusedTextField;
    private int lengthOfString = 0;

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, txtCourseName, txtCourseID, txtCourseFee, txtNumberOfInstallments, txtNumberOfStudents, txtCourseDuration, txtFirstInstallment, txtInstallmentGap, txtFileName, txtMinimumRequirements, txtNotes);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("courseID"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("courseName"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("courseFee"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("numberOfInstallments"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("duration"));
        tblResult.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("courseAvailable"));
        tblResult.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("courseInitiationDate"));

        txtCourseName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            autoFillCourseId();
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllCourses(newValue);
        });

        loadAllCourses("");
    }

    private void loadAllCourses(String keyword) {
        tblResult.getItems().clear();

        for (Course course : courseService.searchCourseByKeyword(keyword)) {
            tblResult.getItems().add(new CourseTM(course.getCourseID(), course.getCourseName(), course.getCourseFee(), course.getNumberOfInstallments(), course.getDuration(), course.isCourseAvailable(), course.getCourseInitiationDate()));
        }
    }

    public void btnAddFile_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddFile_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        addCourse(false);
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addCourse(false);
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
        addCourse(true);
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addCourse(true);
        }
    }

    private void addCourse(boolean isUpdateCourse) {
        trimTextFields(txtFirstInstallment, txtInstallmentGap, txtCourseDuration, txtNumberOfStudents, txtNumberOfInstallments, txtCourseFee, txtCourseID, txtCourseName, txtNotes, txtMinimumRequirements);

        if (!isValidated()) {
            return;
        }

        Course course = new Course(txtCourseID.getText(),
                txtCourseName.getText(),
                Double.parseDouble(txtCourseFee.getText()),
                Integer.parseInt(txtNumberOfInstallments.getText()),
                Integer.parseInt(txtNumberOfStudents.getText()),
                txtCourseDuration.getText(),
                txtFirstInstallment.getText(),
                txtInstallmentGap.getText(),
                rbnCourseStatus.getToggles().get(0).isSelected(),
                txtFileName.getText(),
                txtMinimumRequirements.getText(),
                txtNotes.getText(),
                LocalDate.now());

        try {
            if (isUpdateCourse) {
                courseService.updateCourse(course);
            } else {
                courseService.addCourse(course);
            }
            loadAllCourses("");
            String alertMessage = isUpdateCourse ? "Course have been updated successfully" : "Course have been added successfully";
            new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK).show();
            clearAll();
            txtCourseName.requestFocus();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Course already exist for this Course ID " + txtCourseID.getText()).show();
            txtCourseID.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtCourseName.requestFocus();
        }
    }

    private boolean isValidated() {

        if (!txtCourseFee.getText().matches("((\\d{1,3}(,\\d{3})+)|\\d+)([.]\\d{1,2})?")) {
            new Alert(Alert.AlertType.ERROR, "Invalid course fee", ButtonType.OK).show();
            txtCourseFee.requestFocus();
            return false;
        } else if (!txtNumberOfInstallments.getText().matches("\\d")) {
            new Alert(Alert.AlertType.ERROR, "Invalid number of installments", ButtonType.OK).show();
            txtNumberOfInstallments.requestFocus();
            return false;
        } else if (!txtNumberOfStudents.getText().matches("\\d")) {
            new Alert(Alert.AlertType.ERROR, "Invalid number of students", ButtonType.OK).show();
            txtNumberOfStudents.requestFocus();
            return false;
        } else if (!txtFirstInstallment.getText().matches("\\d")) {
            new Alert(Alert.AlertType.ERROR, "Invalid first installment", ButtonType.OK).show();
            txtFirstInstallment.requestFocus();
            return false;
        } else if (!txtInstallmentGap.getText().matches("\\d")) {
            new Alert(Alert.AlertType.ERROR, "Invalid installment gap", ButtonType.OK).show();
            txtInstallmentGap.requestFocus();
            return false;
        } else if (!txtCourseDuration.getText().matches("\\d")) {
            new Alert(Alert.AlertType.ERROR, "Invalid course duration", ButtonType.OK).show();
            txtCourseDuration.requestFocus();
            return false;
        }

        return true;
    }

    private void trimTextFields(TextInputControl... textFields) {
        for (TextInputControl textField : textFields) {
            textField.setText(textField.getText().trim());
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

    private void clearAll() {
        txtCourseName.clear();
        txtCourseID.clear();
        txtCourseFee.clear();
        txtNumberOfInstallments.clear();
        txtNumberOfStudents.clear();
        chkUnlimitedStudents.setSelected(false);
        txtCourseDuration.clear();
        rbnDuration.getToggles().get(0).setSelected(true);
        txtInstallmentGap.clear();
        rbnInstallmentGap.getToggles().get(0).setSelected(true);
        txtFirstInstallment.clear();
        rbnFirstInstallment.getToggles().get(0).setSelected(true);
        rbnCourseStatus.getToggles().get(0).setSelected(true);
        txtFileName.setText("-Click button to add-");
        txtMinimumRequirements.clear();
        txtNotes.clear();
        txtSearch.clear();
        loadAllCourses("");
        isFirstFocusLostFromCourseId = true;
        setDisableAll(false);
    }

    private void setDisableAll(boolean value) {
        txtCourseName.setDisable(value);
        txtCourseID.setDisable(value);
        txtCourseFee.setDisable(value);
        txtNumberOfInstallments.setDisable(value);
        txtNumberOfStudents.setDisable(value);
        chkUnlimitedStudents.setDisable(value);
        txtCourseDuration.setDisable(value);
        ((RadioButton) (rbnDuration.getToggles().get(0))).getParent().setDisable(value);
        txtInstallmentGap.setDisable(value);
        txtFirstInstallment.setDisable(value);
        ((RadioButton) (rbnCourseStatus.getToggles().get(0))).getParent().setDisable(value);
        txtFileName.setDisable(value);
        txtMinimumRequirements.setDisable(value);
        txtNotes.setDisable(value);
        txtSearch.setDisable(value);
        btnAdd.setDisable(value);
        btnUpdate.setDisable(value);
        btnEdit.setDisable(!value);
        courseIdToUpdate = value ? "" : txtCourseID.getText();
        if (!value) {
            txtCourseID.requestFocus();
            txtCourseID.selectAll();
        }
    }

    private void autoFillCourseId() {
        String input = txtCourseName.getText().trim();
        if (!txtCourseID.getText().isEmpty() || input.isEmpty()) {
            return;
        }
        Matcher matcher = Pattern.compile("\\s+").matcher(input);
        String output = input.charAt(0)+"";
        while (matcher.find()) {
            output += input.charAt(matcher.end());
        }
        txtCourseID.setText(output.toUpperCase());
    }
}
