package controller;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.MaterialUI;

public class ManageCoursesAdminFormController {
    public TextField txtSearch;
    public TextField txtCourseName;
    public TextField txtCourseID;
    public TextField txtCourseFee;
    public TextField txtNumberOfInstallments;
    public TextField txtNumberOfStudents;
    public TextField txtCourseDuration;
    public TextField txtFirstInstallment;
    public TextField txtInstallmentGap;
    public TextField txtFileName;
    public TextArea txtMinimumRequirements;
    public TextArea txtNotes;

    public ToggleGroup rbnFirstInstallment;
    public ToggleGroup rbnInstallmentGap;
    public ToggleGroup rbnGender;
    public ToggleGroup rbnDuration;

    public void initialize(){
        MaterialUI.paintTextFields(txtSearch,txtCourseName,txtCourseID,txtCourseFee,txtNumberOfInstallments,txtNumberOfStudents,txtCourseDuration,txtFirstInstallment,txtInstallmentGap,txtFileName,txtMinimumRequirements,txtNotes);
    }
}
