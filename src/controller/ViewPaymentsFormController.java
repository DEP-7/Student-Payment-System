package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.MaterialUI;

public class ViewPaymentsFormController {
    public ToggleGroup rbtnDayGaps;
    public TextField txtFromDate;
    public TextField txtReceiptNumber;
    public TextField txtNIC;
    public JFXComboBox cmbCourseId;
    public JFXComboBox cmbBatchNumber;
    public TextField txtPaymentDescription;
    public TextField txtPaymentMethod;
    public TextField txtOnlineReference;
    public TextField txtCardNumber;
    public TextField txtExpirationDate;
    public TextField txtNameOnCard;
    public TextField txtTotalAmount;
    public TextField txtAmountReceived;
    public TextField txtBalanceAmount;
    public TextField txtDueDate;
    public TextField txtBalanceSettledDate;
    public TextField txtReceiptDate;
    public TextField txtCashier;
    public TextField txtNotes;
    public TextField txtSearch;
    public TextField txtToDate;

    public void initialize(){
        MaterialUI.paintTextFields(txtFromDate,txtReceiptNumber,txtNIC,cmbCourseId,cmbBatchNumber,txtPaymentDescription,txtPaymentMethod,txtOnlineReference,txtCardNumber,txtExpirationDate,txtNameOnCard,txtTotalAmount,txtAmountReceived,txtBalanceAmount,txtDueDate,txtBalanceSettledDate,txtReceiptDate,txtCashier,txtNotes,txtSearch,txtToDate);
        }
}
