package controller;

import com.jfoenix.controls.JFXRippler;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainFormController {
    public AnchorPane pneItemManageStudents;
    public AnchorPane pneItemAddNewPayment;
    public AnchorPane pneItemManageBatches;
    public AnchorPane pneItemManageCourses;
    public AnchorPane pneItemViewPayments;
    public AnchorPane pneItemViewCourses;
    public AnchorPane pneItemViewBatches;
    public AnchorPane pneItemManageUsers;
    public AnchorPane pneItemDashboard;
    public AnchorPane pneStage;
    public AnchorPane pneClock;
    public JFXRippler rprManageStudents;
    public JFXRippler rprAddNewPayment;
    public JFXRippler rprManageBatches;
    public JFXRippler rprManageCourses;
    public JFXRippler rprViewPayments;
    public JFXRippler rprViewCourses;
    public JFXRippler rprViewBatches;
    public JFXRippler rprManageUsers;
    public JFXRippler rprDashboard;
    public ImageView imgSecondsHand;
    public ImageView imgMinutesHand;
    public ImageView imgHoursHand;
    public Label lblTitle;
    public Label lblTime;
    public VBox pneItemContainer;

    private User loggedUser;
    private ArrayList<Parent> formArray = new ArrayList<>();

    public void initialize() throws IOException {
        startClock();

        rprManageStudents.setControl(pneItemManageStudents);
        rprAddNewPayment.setControl(pneItemAddNewPayment);
        rprManageBatches.setControl(pneItemManageBatches);
        rprManageCourses.setControl(pneItemManageCourses);
        rprViewPayments.setControl(pneItemViewPayments);
        rprViewCourses.setControl(pneItemViewCourses);
        rprViewBatches.setControl(pneItemViewBatches);
        rprManageUsers.setControl(pneItemManageUsers);
        rprDashboard.setControl(pneItemDashboard);

        // Please update below array by inserting fxml file name, when adding new forms
        String[] urlFormNames = {"DashboardForm", "AddNewPaymentForm", "ViewPaymentsForm", "ManageStudentsForm", "ViewCoursesForm", "ViewBatchesForm", "ManageBatchesAdminForm", "ManageCoursesAdminForm", "ManageUsersAdminForm"};

        for (int i = 0; i < urlFormNames.length; i++) {
            formArray.add(FXMLLoader.load(this.getClass().getResource("../view/" + urlFormNames[i] + ".fxml")));
            AnchorPane.setRightAnchor(formArray.get(i), 0.0);
            AnchorPane.setLeftAnchor(formArray.get(i), 0.0);
            AnchorPane.setTopAnchor(formArray.get(i), 0.0);
            AnchorPane.setBottomAnchor(formArray.get(i), 0.0);
        }

        pneItemContainer.setUserData(new boolean[9]); // Set notifications to the forms
        /*0 - Dashboard
         * 1 - Add New Payment
         * 2 - View Payments
         * 3 - Manage Students
         * 4 - View Courses
         * 5 - View Batches
         * 6 - Manage Batches
         * 7 - Manage Courses
         * 8 - Manage Users*/

        Platform.runLater(() -> {
            loggedUser = (User) pneStage.getScene().getWindow().getUserData();

//            rprViewCourses.setVisible(!loggedUser.isAdmin());
//            rprManageCourses.setVisible(loggedUser.isAdmin());
//            rprViewBatches.setVisible(!loggedUser.isAdmin());
//            rprManageBatches.setVisible(loggedUser.isAdmin());
//            rprManageUsers.setVisible(loggedUser.isAdmin());
//
//            pneItemContainer.getChildren().remove(loggedUser.isAdmin() ? rprViewCourses : rprManageCourses);
//            pneItemContainer.getChildren().remove(loggedUser.isAdmin() ? rprViewBatches : rprManageBatches);
//
//            if (loggedUser.getUsername().equals(loggedUser.getNic()) && loggedUser.isPasswordCorrect(loggedUser.getNic())) {
//                // TODO: Guidelines to change password
//            }
        });
    }

    public void pneDashboard_OnMouseClicked(MouseEvent mouseEvent) {
        load(0, "Dashboard");
    }

    public void pneAddNewPayment_OnMouseClicked(MouseEvent mouseEvent) {
        load(1, "Dashboard / Add New Payment");
    }

    public void pneViewPayments_OnMouseClicked(MouseEvent mouseEvent) {
        load(2, "Dashboard / View Payments");
    }

    public void pneManageStudents_OnMouseClicked(MouseEvent mouseEvent) {
        load(3, "Dashboard / Manage Students");
    }

    public void pneViewCourses_OnMouseClicked(MouseEvent mouseEvent) {
        load(4, "Dashboard / View Courses");
    }

    public void pneViewBatches_OnMouseClicked(MouseEvent mouseEvent) {
        load(5, "Dashboard / View Batches");
    }

    public void pneManageBatches_OnMouseClicked(MouseEvent mouseEvent) {
        load(6, "Dashboard / Manage Batches");
    }

    public void pneManageCourses_OnMouseClicked(MouseEvent mouseEvent) {
        load(7, "Dashboard / Manage Courses");
    }

    public void pneManageUsers_OnMouseClicked(MouseEvent mouseEvent) {
        load(8, "Dashboard / Manage Users");
    }

    public void load(int urlIndex, String currentFormLocation) {
        if (pneStage.getChildren().get(0) == formArray.get(urlIndex)) {
            return;
        }

        pneStage.getChildren().clear();
        pneStage.getChildren().add(formArray.get(urlIndex));
        lblTitle.setText(currentFormLocation);

        FadeTransition transition = new FadeTransition(Duration.millis(300), pneStage);
        transition.setFromValue(0.5);
        transition.setToValue(1);
        transition.play();

        pneClock.setVisible(pneStage.getChildren().get(0) == formArray.get(0));
    }

    public void imgSettings_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Stage settingsStage = new Stage();
        settingsStage.setUserData(loggedUser);
        Parent load = FXMLLoader.load(this.getClass().getResource("../view/SettingsForm.fxml"));
        Scene settingScene = new Scene(load);
        settingsStage.setScene(settingScene);
        settingsStage.setTitle("Settings");
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.initOwner(pneStage.getScene().getWindow());
        settingsStage.setResizable(false);
        settingsStage.centerOnScreen();
        settingsStage.show();
    }

    public void pneItemDashbord_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(0, "Dashboard");
        }
    }

    public void pneAddNewPayment_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(1, "Dashboard / Add New Payment");
        }
    }

    public void pneViewPayments_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(2, "Dashboard / View Payments");
        }
    }

    public void pneManageStudents_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(3, "Dashboard / Manage Students");
        }
    }

    public void pneViewCourses_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(4, "Dashboard / View Courses");
        }
    }

    public void pneViewBatches_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(5, "Dashboard / View Batches");
        }
    }

    public void pneManageCourses_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(6, "Dashboard / Manage Courses");
        }
    }

    public void pneManageUsers_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(7, "Dashboard / Manage Users");
        }
    }

    public void pneManageBatches_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            load(8, "Dashboard / Manage Batches");
        }
    }

    public void pneItemDashbord_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprDashboard.createManualRipple().run();
        }
    }

    public void pneAddNewPayment_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprAddNewPayment.createManualRipple().run();
        }
    }

    public void pneViewPayments_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprViewPayments.createManualRipple().run();
        }
    }

    public void pneManageStudents_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprManageStudents.createManualRipple().run();
        }
    }

    public void pneViewCourses_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprViewCourses.createManualRipple().run();
        }
    }

    public void pneViewBatches_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprViewBatches.createManualRipple().run();
        }
    }

    public void pneManageBatches_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprManageBatches.createManualRipple().run();
        }
    }

    public void pneManageCourses_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprManageCourses.createManualRipple().run();
        }
    }

    public void pneManageUsers_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
            rprManageUsers.createManualRipple().run();
        }
    }

    private void startClock() {
        String time1 = String.format("%tT", new Date());
        String[] arr1 = time1.split(":");

        //Set initial positions to start the clock
        double s = 6 * Double.parseDouble(arr1[2]);
        RotateTransition rs = new RotateTransition(Duration.millis(100), imgSecondsHand);
        rs.setFromAngle(s - 6);
        rs.setToAngle(s);
        rs.play();

        double m = 6 * Double.parseDouble(arr1[1]);
        RotateTransition rm = new RotateTransition(Duration.millis(10), imgMinutesHand);
        rm.setToAngle(m);
        rm.play();

        double h = 30 * (Double.parseDouble(arr1[0]) + Double.parseDouble(arr1[1]) / 60);
        RotateTransition rh = new RotateTransition(Duration.millis(100), imgHoursHand);
        rh.setFromAngle(h - 6);
        rh.setToAngle(h);
        rh.play();

        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            LocalDateTime now = LocalDateTime.now();
            String date = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss a").format(now);
            String time = DateTimeFormatter.ofPattern("hh:mm:ss").format(now);
            lblTime.setText(date);

            double secondsAngle = 6 * now.getSecond();
            RotateTransition rotateSecondHand = new RotateTransition(Duration.millis(100), imgSecondsHand);
            rotateSecondHand.setFromAngle(secondsAngle - 6);
            rotateSecondHand.setToAngle(secondsAngle);
            rotateSecondHand.play();

            if (now.getSecond() == 1) {
                double minutesAngle = 6 * now.getMinute();
                RotateTransition rotateMinuteHand = new RotateTransition(Duration.millis(100), imgMinutesHand);
                rotateMinuteHand.setFromAngle(minutesAngle - 6);
                rotateMinuteHand.setToAngle(minutesAngle);
                rotateMinuteHand.play();

                double hourAngle = 30 * now.getHour() + (now.getMinute() + 1) * 0.5;
                RotateTransition rotateHourHand = new RotateTransition(Duration.millis(100), imgHoursHand);
                rotateHourHand.setFromAngle(hourAngle - 0.5);
                rotateHourHand.setToAngle(hourAngle);
                rotateHourHand.play();
            }
        }));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();
    }
}
