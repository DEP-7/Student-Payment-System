package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Student;
import model.StudentTM;
import service.StudentService;
import util.MaterialUI;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ManageStudentsFormController {
    private StudentService studentService = new StudentService();
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

    public void initialize(){
        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("nic"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("courseId"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("batchNumber"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("contactNumber"));

        MaterialUI.paintTextFields(txtNIC, txtFullName, txtNameWithInitials, txtDateOfBirth, txtHighestEducationalQualification,
                txtPreviousRegisteredCourses, txtAddress, txtContactNumber, txtEmail, txtAge, txtSearch, cmbCourseId, cmbBatchNumber,txtDiscount);
        loadAllStudents();
    }

    private void loadAllStudents() {
        tblResult.getItems().clear();

        for (Student student : studentService.studentDB) {
            tblResult.getItems().add(new StudentTM(student.getNic(),student.getNameWithInitials(),student.getCourseId(),student.getBatchNumber(),student.getContactNumber()));
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        Student student = new Student(txtNIC.getText(),
                txtFullName.getText(),
                txtNameWithInitials.getText(),
                ((RadioButton)(rbnGender.getSelectedToggle())).getText(),
                LocalDate.parse(txtDateOfBirth.getText()),
                imgStudentImage.getImage(),
                txtHighestEducationalQualification.getText(),
                txtAddress.getText(),
                txtContactNumber.getText(),
                txtEmail.getText(),
                cmbCourseId.getValue(),
                Integer.parseInt(cmbBatchNumber.getValue()),
                new BigDecimal(txtDiscount.getText()));

    }
}
