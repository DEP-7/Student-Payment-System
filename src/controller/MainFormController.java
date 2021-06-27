package controller;

import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

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
    public JFXRippler rprManageStudents;
    public JFXRippler rprAddNewPayment;
    public JFXRippler rprManageBatches;
    public JFXRippler rprManageCourses;
    public JFXRippler rprViewPayments;
    public JFXRippler rprViewCourses;
    public JFXRippler rprViewBatches;
    public JFXRippler rprManageUsers;
    public JFXRippler rprDashboard;
    public Label lblTitle;
    public Label lblTime;
    public VBox pneItemContainer;

    User loggedUser;
    Parent[] formArray;

    public void initialize() throws IOException {
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
        String[] urlFormNames = {"DashboardForm", "AddNewPaymentForm", "ViewPaymentsForm", "ManageStudentsForm", "ViewCoursesForm", "ViewBatchesForm", "ManageCoursesAdminForm", "ManageUsersAdminForm", "ManageBatchesAdminForm"};
        formArray = new Parent[urlFormNames.length];

        for (int i = 0; i < formArray.length; i++) {
            formArray[i] = FXMLLoader.load(this.getClass().getResource("../view/" + urlFormNames[i] + ".fxml"));
            AnchorPane.setRightAnchor(formArray[i],0.0);
            AnchorPane.setLeftAnchor(formArray[i],0.0);
            AnchorPane.setTopAnchor(formArray[i],0.0);
            AnchorPane.setBottomAnchor(formArray[i],0.0);
        }

        pneItemContainer.setUserData(new boolean[9]);
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
//
//            rprViewCourses.setVisible(!loggedUser.isAdmin());
//            rprManageCourses.setVisible(loggedUser.isAdmin());
//            rprViewBatches.setVisible(!loggedUser.isAdmin());
//            rprManageBatches.setVisible(loggedUser.isAdmin());
//            rprManageUsers.setVisible(loggedUser.isAdmin());
//
//            pneItemContainer.getChildren().remove(loggedUser.isAdmin()?rprViewCourses:rprManageCourses);
//            pneItemContainer.getChildren().remove(loggedUser.isAdmin()?rprViewBatches:rprManageBatches);
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

    public void pneManageCourses_OnMouseClicked(MouseEvent mouseEvent) {
        load(6, "Dashboard / Manage Courses");
    }

    public void pneManageUsers_OnMouseClicked(MouseEvent mouseEvent) {
        load(7, "Dashboard / Manage Users");
    }

    public void pneManageBatches_OnMouseClicked(MouseEvent mouseEvent) {
        load(8, "Dashboard / Manage Batches");
    }

    private void load(int urlIndex, String currentFormLocation) {
        if (pneStage.getChildren().get(0) == formArray[urlIndex]) {
            return;
        }
        pneStage.getChildren().clear();
        pneStage.getChildren().add(formArray[urlIndex]);
        String[] splittedFormLocationString = currentFormLocation.split("/");
        lblTitle.setText(currentFormLocation);

        AnchorPane.setRightAnchor(formArray[urlIndex],0.0);
        AnchorPane.setLeftAnchor(formArray[urlIndex],0.0);
        AnchorPane.setTopAnchor(formArray[urlIndex],0.0);
        AnchorPane.setBottomAnchor(formArray[urlIndex],0.0);
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
}
