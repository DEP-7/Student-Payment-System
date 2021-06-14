package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    public AnchorPane pneStage;
    public Label lblTitle;

    Parent[] formArray = new Parent[6];

    public void initialize() throws IOException {
        formArray[0] = FXMLLoader.load(this.getClass().getResource("../view/DashboardForm.fxml"));
        formArray[1] = FXMLLoader.load(this.getClass().getResource("../view/AddNewPaymentForm.fxml"));
        formArray[2] = FXMLLoader.load(this.getClass().getResource("../view/ViewPaymentsForm.fxml"));
        formArray[3] = FXMLLoader.load(this.getClass().getResource("../view/ManageStudentsForm.fxml"));
        formArray[4] = FXMLLoader.load(this.getClass().getResource("../view/ViewCoursesForm.fxml"));
        formArray[5] = FXMLLoader.load(this.getClass().getResource("../view/ViewBatchesForm.fxml"));
    }

    public void pneDashboard_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/DashboardForm.fxml", "Dashboard", "Dashboard");
        load(0, "Dashboard", "Dashboard");
    }

    public void pneAddNewPayment_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/AddNewPaymentForm.fxml", "Add New Payment", "Dashboard / Add New Payment");
        load(1, "Add New Payment", "Dashboard / Add New Payment");
    }

    public void pneViewPayments_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/ViewPaymentsForm.fxml", "View Payments", "Dashboard / View Payments");
        load(2, "View Payments", "Dashboard / View Payments");
    }

    public void pneManageStudents_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/ManageStudentsForm.fxml", "Manage Students", "Dashboard / Manage Students");
        load(3, "Manage Students", "Dashboard / Manage Students");
    }

    public void pneViewCourses_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/ViewCoursesForm.fxml", "View Courses", "Dashboard / View Courses");
        load(4, "View Courses", "Dashboard / View Courses");
    }

    public void pneViewBatches_OnMouseClicked(MouseEvent mouseEvent) {
        //loadForm("../view/ViewBatchesForm.fxml", "View Batches", "Dashboard / View Batches");
        load(5, "View Batches", "Dashboard / View Batches");
    }

    private void load(int url, String formName, String currentFormLocation) { // Make sure to update AccessibleText in all main anchor panes
        String accessibleText = pneStage.getChildren().get(0).getAccessibleText();
        if (accessibleText != null && accessibleText.equals(formName)) {
            return;
        }
        pneStage.getChildren().clear();
        pneStage.getChildren().add(formArray[url]);
        Stage mainStage = (Stage) pneStage.getScene().getWindow();
        mainStage.setTitle(formName);
        lblTitle.setText(currentFormLocation);
        Platform.runLater(() -> {
            //mainStage.setMaximized(true);
            mainStage.centerOnScreen();
            mainStage.sizeToScene();
        });
    }

    private void loadForm(String url, String formName, String currentFormLocation) { // Make sure to update AccessibleText in all main anchor panes
        String accessibleText = pneStage.getChildren().get(0).getAccessibleText();
        if (accessibleText != null && accessibleText.equals(formName)) {
            return;
        }
        pneStage.getChildren().clear();
        try {
            Parent formLoader = FXMLLoader.load(this.getClass().getResource(url));
            pneStage.getChildren().add(formLoader);
            Stage mainStage = (Stage) pneStage.getScene().getWindow();
            mainStage.setTitle(formName);
            lblTitle.setText(currentFormLocation);
            Platform.runLater(() -> {
                //mainStage.setMaximized(true);
                mainStage.centerOnScreen();
                mainStage.sizeToScene();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imgSettings_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Stage settingsStage = new Stage();
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
}
