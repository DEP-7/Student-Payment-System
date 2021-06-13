package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.MaterialUI;

public class AddNewPaymentFormController {
    public TextField txtNIC;
    public TextField txtReceiptNumber;
    public JFXTextField txtUsername;
    public TextField txtOnlineReferencNumber;
    public TextField txtFileName;
    public TextField txtCardNumber;
    public TextField txtExpirationDate;
    public TextField txtNameOnCard;
    public TextField txtAmountReceived;
    public TextField txtDueDateOfBalancePayment;
    public TextField txtPaymentDate;
    public TextField txtTotalAmount;
    public TextField txtBalanceAmount;
    public TextArea txtNotes;

    public void initialize() {
        MaterialUI.paintTextFields(txtNIC, txtReceiptNumber, txtOnlineReferencNumber, txtFileName, txtCardNumber, txtExpirationDate, txtNameOnCard, txtAmountReceived, txtDueDateOfBalancePayment, txtPaymentDate, txtTotalAmount, txtBalanceAmount, txtNotes);
    }
}
