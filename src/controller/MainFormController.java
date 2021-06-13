package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    public AnchorPane pneStage;
    public Label lblTitle;

    public void pneDashboard_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void pneAddNewPayment_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        String accessibleText = pneStage.getChildren().get(0).getAccessibleText();
        if (accessibleText != null && accessibleText.equals("Add New Payment")) {
            System.out.println("return");
            return;
        }
        pneStage.getChildren().clear();
        Parent addNewPaymentFormLoader = FXMLLoader.load(this.getClass().getResource("../view/AddNewPaymentForm.fxml"));
        pneStage.getChildren().add(addNewPaymentFormLoader);
        Stage mainStage = (Stage) pneStage.getScene().getWindow();
        mainStage.setTitle("Add New Payment");
        lblTitle.setText("Dashboard / Add New Payment");
        Platform.runLater(() -> {
            //mainStage.setMaximized(true);
            mainStage.centerOnScreen();
            mainStage.sizeToScene();
        });
        System.out.println(pneStage.getChildren().get(0).getAccessibleText());
    }

    public void pneViewPayments_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void pneManageStudents_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void pneViewCourses_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void pneViewBatches_OnMouseClicked(MouseEvent mouseEvent) {
    }
}
