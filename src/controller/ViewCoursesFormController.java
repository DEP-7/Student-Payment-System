/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Course;
import model.CourseTM;
import service.CourseServiceRedisImpl;
import service.exception.NotFoundException;
import util.MaterialUI;

public class ViewCoursesFormController {
    private final CourseServiceRedisImpl courseService = new CourseServiceRedisImpl();
    public TableView<CourseTM> tblResult;
    public TextField txtSearch;
    public Label lblEntryRequirements;
    public Label lblNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtSearch, lblEntryRequirements, lblNotes);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("courseID"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("courseName"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("duration"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("courseFee"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("numberOfInstallments"));
        tblResult.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("courseStatus"));

        tblResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                CourseTM selectedCourseTM = tblResult.getSelectionModel().getSelectedItem();

                try {
                    Course selectedCourse = courseService.searchCourse(selectedCourseTM.getCourseID());
                    lblEntryRequirements.setText(selectedCourse.getMinimumRequirements());
                    lblNotes.setText(selectedCourse.getNotes());
                    // TODO: Connect file location to button
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong. Please contact developer").show();
                    txtSearch.requestFocus();
                }
            } else {
                lblEntryRequirements.setText("");
                lblNotes.setText("");
                // TODO: Disconnect file location to button
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllCourses(newValue);
        });

        loadAllCourses("");
    }

    private void loadAllCourses(String keyword) {
        tblResult.getItems().clear();

        for (Course course : courseService.searchCourseByKeyword(keyword)) {
            tblResult.getItems().add(new CourseTM(course.getCourseID(), course.getCourseName(), course.getCourseFee(), course.getNumberOfInstallments(), course.getDuration(), course.getCourseStatus(), course.getCourseInitiationDate()));
        }
    }

    public void btnCourseSchedule_OnAction(ActionEvent actionEvent) {
        openFile();
    }

    public void btnCourseSchedule_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            openFile();
        }
    }

    private void openFile() {
        // TODO: Open course schedule.pdf files
    }
}
