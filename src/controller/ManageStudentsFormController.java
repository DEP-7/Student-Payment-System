package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Student;
import model.StudentTM;
import service.StudentService;
import service.exception.DuplicateEntryException;
import util.MaterialUI;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ManageStudentsFormController {
    public TableView<StudentTM> tblResult;
    public JFXComboBox<String> cmbBatchNumber;
    public JFXComboBox<String> cmbCourseId;
    public ToggleGroup rbnGender;
    public ImageView imgStudentImage;
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
    private final StudentService studentService = new StudentService();

    public void initialize() {
        cmbBatchNumber.getItems().add("001");

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("nic"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("courseId"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("batchNumber"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("contactNumber"));

        MaterialUI.paintTextFields(txtNIC, txtFullName, txtNameWithInitials, txtDateOfBirth, txtHighestEducationalQualification,
                txtPreviousRegisteredCourses, txtAddress, txtContactNumber, txtEmail, txtAge, txtSearch, cmbCourseId, cmbBatchNumber, txtDiscount);
        loadAllStudents();
    }

    private void loadAllStudents() {
        tblResult.getItems().clear();

        for (Student student : StudentService.studentDB) {
            tblResult.getItems().add(new StudentTM(student.getNic(), student.getNameWithInitials(), student.getCourseId(), student.getBatchNumber(), student.getContactNumber()));
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        trimAllTextFields(txtNIC, txtAddress, txtContactNumber, txtEmail, txtDateOfBirth, txtFullName, txtNameWithInitials, txtHighestEducationalQualification);
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
            studentService.addStudent(student);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION, "Student have been saved successfully", ButtonType.OK);
            alertSuccess.setHeaderText(null);
            alertSuccess.show();
        } catch (DuplicateEntryException e) {
            Alert alertUnsuccess = new Alert(Alert.AlertType.ERROR, "Student already exist for this NIC " + txtNIC.getText());
            alertUnsuccess.setHeaderText(null);
            alertUnsuccess.show();
            txtNIC.requestFocus();
        }
    }

    private void trimAllTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }
}
