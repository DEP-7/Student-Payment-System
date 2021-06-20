package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Student;
import model.StudentTM;
import service.StudentService;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static util.ValidationUtil.*;

public class ManageStudentsFormController {
    private final StudentService studentService = new StudentService();
    public TableView<StudentTM> tblResult;
    public JFXComboBox<String> cmbBatchNumber;
    public JFXComboBox<String> cmbCourseId;
    public JFXRadioButton rbnFemale;
    public JFXRadioButton rbnMale;
    public ToggleGroup rbnGender;
    public ImageView imgStudentImage;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public TextField txtHighestEducationalQualification;
    public TextField txtPreviousRegisteredCourses;
    public TextField txtNameWithInitials;
    public TextField txtContactNumber;
    public TextField txtDiscount;
    public TextField txtFullName;
    public TextField txtDateOfBirth;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtEmail;
    public TextField txtNIC;
    public TextField txtAge;
    private ArrayList<Integer> caretPositionArray;
    private TextField previouslyFocusedTextField;
    private final String studentNICToUpdate = "";
    private int lengthOfString = 0;

    public void initialize() {
        cmbBatchNumber.getItems().add("001");
        cmbCourseId.getItems().add("DEP");
        cmbBatchNumber.getItems().add("001");
        cmbCourseId.getItems().add("DEP");

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("nic"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("courseId"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("batchNumber"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("contactNumber"));

        MaterialUI.paintTextFields(txtNIC, txtFullName, txtNameWithInitials, txtDateOfBirth, txtHighestEducationalQualification,
                txtPreviousRegisteredCourses, txtAddress, txtContactNumber, txtEmail, txtAge, txtSearch, cmbCourseId, cmbBatchNumber, txtDiscount);

        txtDateOfBirth.textProperty().addListener((observable, oldValue, newValue) -> {
            String inputText = txtDateOfBirth.getText();

            if (inputText.length() == 4 || inputText.length() == 7 || inputText.length() == 10) {
                LocalDate today = LocalDate.now();
                Period period = null;
                LocalDate dob;

                try {
                    if (inputText.length() == 4) {
                        inputText = inputText + "-" + String.format("%02d", today.getMonthValue()) + "-" + today.getDayOfMonth();
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    } else if (inputText.length() == 7) {
                        inputText = inputText + "-" + today.getDayOfMonth();
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    } else if (inputText.length() == 10) {
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    }

                    if (period.getYears() >= 0 && period.getMonths() >= 0 && period.getDays() >= 0) {
                        txtAge.setText(period.getYears() + "-" + period.getMonths() + "-" + period.getDays());
                    } else {
                        txtAge.setText("Invalid");
                    }

                } catch (DateTimeParseException e) {
                    txtAge.setText("Invalid");
                }
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllStudents(newValue);
        });

        loadAllStudents("");
    }

    private void loadAllStudents(String keyword) {
        tblResult.getItems().clear();

        for (Student student : studentService.searchStudentsByKeyword(keyword)) {
            tblResult.getItems().add(new StudentTM(student.getNic(), student.getNameWithInitials(), student.getCourseId(), student.getBatchNumber(), student.getContactNumber()));
        }
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addStudent(false);
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        addStudent(false);
    }

    private void addStudent(boolean isUpdateStudent) {
        trimTextFields(txtEmail, txtAddress, txtFullName, txtDiscount, txtContactNumber, txtNameWithInitials, txtHighestEducationalQualification);

        if (!isValidated()) {
            return;
        }

        Student student = new Student(txtNIC.getText(),
                txtFullName.getText(),
                txtNameWithInitials.getText(),
                ((RadioButton) (rbnGender.getSelectedToggle())).getText(),
                LocalDate.parse(txtDateOfBirth.getText()),
                imgStudentImage.getImage(),
                txtHighestEducationalQualification.getText(),
                txtAddress.getText(),
                txtContactNumber.getText(),
                txtEmail.getText(),
                cmbCourseId.getValue(),
                Integer.parseInt(cmbBatchNumber.getValue()),
                new BigDecimal(txtDiscount.getText()));

        try {
            if (isUpdateStudent) {
                studentService.updateStudent(student, studentNICToUpdate);
            }else {
                studentService.addStudent(student);
            }
            loadAllStudents("");
            String alertMessage=isUpdateStudent?"Student have been updated successfully":"Student have been added successfully";
            new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK).show();
            clearAll();
            txtNIC.requestFocus();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Student already exist for this NIC " + txtNIC.getText()).show();
            txtNIC.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtNIC.requestFocus();
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
        txtNIC.clear();
        txtFullName.clear();
        txtNameWithInitials.clear();
        rbnMale.setSelected(true);
        txtDateOfBirth.clear();
        txtHighestEducationalQualification.clear();
        txtPreviousRegisteredCourses.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        cmbCourseId.getSelectionModel().clearSelection();
        cmbBatchNumber.getSelectionModel().clearSelection();
        txtDiscount.setText("0.00%");
        txtAge.setText("00-00-00");
        txtSearch.clear();
        loadAllStudents("");
    }

    private boolean isValidated() {

        if (!isValidNIC(txtNIC.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC", ButtonType.OK).show();
            txtNIC.requestFocus();
            return false;
        } else if (!isValidFullName(txtFullName.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid full name", ButtonType.OK).show();
            txtFullName.requestFocus();
            return false;
        } else if (!isValidNameWithInitials(txtNameWithInitials.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid name", ButtonType.OK).show();
            txtNameWithInitials.requestFocus();
            return false;
        } else if (!isValidPastDate(txtDateOfBirth.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth", ButtonType.OK).show();
            txtDateOfBirth.requestFocus();
            return false;
        } else if (!isValidPercentage(txtDiscount.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid discount", ButtonType.OK).show();
            txtDiscount.requestFocus();
            return false;
        } else if (!isValidAddress(txtAddress.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid address", ButtonType.OK).show();
            txtAddress.requestFocus();
            return false;
        } else if (!isValidContactNumber(txtContactNumber.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number", ButtonType.OK).show();
            txtContactNumber.requestFocus();
            return false;
        } else if (!isValidEmail(txtEmail.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number", ButtonType.OK).show();
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void trimTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }

    public void txtContactNumber_OnKeyReleased(KeyEvent keyEvent) {
        separateString(keyEvent, txtContactNumber, "-", 3, 7);
    }

    public void txtNIC_OnKeyReleased(KeyEvent keyEvent) {
        if (txtNIC.getText().length() > 10) {
            int caretPosition = txtNIC.getCaretPosition();
            txtNIC.setText(txtNIC.getText().replaceAll("[Vv]", ""));
            txtNIC.positionCaret(caretPosition);
            separateString(keyEvent, txtNIC, "", 12);
            return;
        }

        separateString(keyEvent, txtNIC, "V", 9);
    }

    public void txtDateOfBirth_OnKeyReleased(KeyEvent keyEvent) {
        separateString(keyEvent, txtDateOfBirth, "-", 4, 2, 2);
    }

    private void separateString(KeyEvent keyEvent, TextField currentTextField, String separator, int... separatingLengths) {
        String text = currentTextField.getText();
        int caretPosition = currentTextField.getCaretPosition();
        boolean isCaretAtLast = currentTextField.getText().length() == caretPosition;

        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            if (caretPositionArray.contains(caretPosition)) {
                text = text.substring(0, caretPosition - 1) + text.substring(caretPosition);
                caretPosition -= 1;
            }
        }

        text = text.replaceAll("[^0-9]", "");
        lengthOfString = previouslyFocusedTextField == currentTextField ? lengthOfString : calculateLengthOfString(separatingLengths); // Reduce iteration when typing in same text field
        String newText = "";
        int indexTotal = 0;
        int indexTotalPrevious = 0;

        for (int separatingLength : separatingLengths) {
            indexTotalPrevious += separatingLength;
            if (text.length() > indexTotal) {
                if (text.length() >= indexTotalPrevious) {
                    newText += text.substring(indexTotal, indexTotalPrevious) + separator;
                } else {
                    newText += text.substring(indexTotal);
                    break;
                }
                indexTotal += separatingLength;
            } else {
                break;
            }
        }

        if (separatingLengths.length > 1 && keyEvent.getText().matches("\\d") && separator.equals(Character.toString(newText.charAt(caretPosition - 1)))) { // 1993|-5 -> 1993-|25 caret position error fixing 1993-2|5
            caretPosition += 1;
        }

        currentTextField.setText(lengthOfString <= text.length() && separatingLengths.length != 1 ? newText.substring(0, newText.length() - 1) : newText);
        currentTextField.positionCaret(isCaretAtLast ? newText.length() : caretPosition);
        previouslyFocusedTextField = currentTextField;
    }

    private int calculateLengthOfString(int[] integerArray) {
        int total = 0;
        caretPositionArray = new ArrayList<>();

        for (int i = 0; i < integerArray.length; i++) {
            total += integerArray[i];
            caretPositionArray.add(total + i);
        }

        return total;
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
        addStudent(true);
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
        addStudent(true);
    }

    private void setDisableAll(boolean value) {
        txtNIC.setDisable(value);
        txtDiscount.setDisable(value);
        txtFullName.setDisable(value);
        txtNameWithInitials.setDisable(value);
        rbnMale.setDisable(value);
        rbnFemale.setDisable(value);
        txtDateOfBirth.setDisable(value);
        txtAge.setDisable(value);
        txtHighestEducationalQualification.setDisable(value);
        txtPreviousRegisteredCourses.setDisable(value);
        txtAddress.setDisable(value);
        txtContactNumber.setDisable(value);
        txtEmail.setDisable(value);
        cmbCourseId.setDisable(value);
        cmbBatchNumber.setDisable(value);
        btnAdd.setDisable(value);
        btnUpdate.setDisable(value);
        btnEdit.setDisable(!value);
    }
}
