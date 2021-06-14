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

    Parent[] formArray;

    public void initialize() throws IOException {
        String[] urlFormNames={"DashboardForm","AddNewPaymentForm","ViewPaymentsForm","ManageStudentsForm","ViewCoursesForm","ViewBatchesForm"};
        formArray = new Parent[urlFormNames.length];
        for (int i = 0; i < formArray.length; i++) {
            formArray[i] = FXMLLoader.load(this.getClass().getResource("../view/"+urlFormNames[i]+".fxml"));
        }
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

    private void load(int urlIndex, String currentFormLocation) {
        //String accessibleText = pneStage.getChildren().get(0).getAccessibleText();
        System.out.println(pneStage.getChildren().get(0));
        System.out.println(formArray[urlIndex]);
        System.out.println();
        if (pneStage.getChildren().get(0)==formArray[urlIndex]) {
            return;
        }
        pneStage.getChildren().clear();
        pneStage.getChildren().add(formArray[urlIndex]);
        Stage mainStage = (Stage) pneStage.getScene().getWindow();
        String[] splittedFormLocationString = currentFormLocation.split("/");
        mainStage.setTitle(splittedFormLocationString[splittedFormLocationString.length-1].trim());
        lblTitle.setText(currentFormLocation);
        Platform.runLater(() -> {
            //mainStage.setMaximized(true);
            mainStage.centerOnScreen();
            mainStage.sizeToScene();
        });
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
