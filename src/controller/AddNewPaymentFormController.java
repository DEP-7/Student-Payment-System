package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import util.MaterialUI;

public class AddNewPaymentFormController {
    public JFXTextField txtNameWithInitials;
    public JFXTextField txtContactNumber;
    public JFXTextField txtBatchNumber;
    public JFXTextField txtCustomText;
    public JFXTextField txtFullName;
    public JFXTextField txtCourseId;
    public JFXTextField txtDiscount;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public ToggleGroup rbnPaymentDescription;
    public ToggleGroup rbnPaymentMethod;
    public ImageView imgMasterCard;
    public ImageView imgProfilePic;
    public ImageView imgVisaCard;
    public TextField txtDueDateOfBalancePayment;
    public TextField txtOnlineReferenceNumber;
    public TextField txtExpirationDate;
    public TextField txtAmountReceived;
    public TextField txtReceiptNumber;
    public TextField txtBalanceAmount;
    public TextField txtPaymentDate;
    public TextField txtTotalAmount;
    public TextField txtCardNumber;
    public TextField txtNameOnCard;
    public TextField txtFileName;
    public TextField txtNIC;
    public TextArea txtNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtNIC, txtReceiptNumber, txtOnlineReferenceNumber, txtFileName, txtCardNumber, txtExpirationDate, txtNameOnCard, txtAmountReceived, txtDueDateOfBalancePayment, txtPaymentDate, txtTotalAmount, txtBalanceAmount, txtNotes);
    }

    public void btnAddFile_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddFile_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnMakePayment_OnAction(ActionEvent actionEvent) {
    }

    public void btnMakePayment_OnKeyPressed(KeyEvent keyEvent) {
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
    }
}
