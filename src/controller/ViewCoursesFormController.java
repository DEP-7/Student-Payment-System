package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.MaterialUI;

public class ViewCoursesFormController {
    public TextField txtSearch;
    public Label lblEntryRequirements;
    public Label lblNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, lblEntryRequirements, lblNotes);
    }
}
