package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Batch;
import model.Course;
import model.Receipt;
import model.ReceiptTM;
import model.sub.CardPayment;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import model.sub.PaymentMethod;
import service.BatchService;
import service.CourseService;
import service.ReceiptService;
import service.StudentService;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static util.ValidationUtil.isValidNIC;
import static util.ValidationUtil.isValidPastDate;

public class ViewPaymentsFormController {
    private final ReceiptService receiptService = new ReceiptService();
    private final CourseService courseService = new CourseService();
    private final BatchService batchService = new BatchService();
    private final StudentService studentService = new StudentService();
    public TableView<ReceiptTM> tblResult;
    public ToggleGroup rbnDayGaps;
    public JFXButton btnBalancePaymentReceipt;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public TextField txtBalanceSettledDate;
    public TextField txtPaymentDescription;
    public TextField txtOnlineReference;
    public TextField txtExpirationDate;
    public TextField txtSearchFromDate;
    public TextField txtAmountReceived;
    public TextField txtReceiptNumber;
    public TextField txtPaymentMethod;
    public TextField txtBalanceAmount;
    public TextField txtSearchToDate;
    public TextField txtTotalAmount;
    public TextField txtPaymentDate;
    public TextField txtBatchNumber;
    public TextField txtNameOnCard;
    public TextField txtCardNumber;
    public TextField txtCourseId;
    public TextField txtDueDate;
    public TextField txtCashier;
    public TextField txtSearch;
    public TextField txtNotes;
    public TextField txtNIC;

    MainFormController mainFormController;

    public void initialize() {
        MaterialUI.paintTextFields(txtNIC, txtNotes, txtSearch, txtCashier, txtDueDate, txtCardNumber, txtNameOnCard, txtPaymentDate, txtTotalAmount, txtSearchToDate, txtBalanceAmount, txtPaymentMethod, txtReceiptNumber, txtAmountReceived, txtSearchFromDate, txtExpirationDate, txtOnlineReference, txtPaymentDescription, txtBalanceSettledDate, txtCourseId, txtBatchNumber);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("receiptNumber"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("courseId"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("paymentDescription"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("studentId"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("userName"));
        tblResult.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("receiptDate"));
        tblResult.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("balancePayment"));

        tblResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                ReceiptTM selectedReceiptTM = tblResult.getSelectionModel().getSelectedItem();

                try {
                    Receipt selectedReceipt = receiptService.searchReceipt(selectedReceiptTM.getReceiptNumber());
                    txtReceiptNumber.setText(selectedReceipt.getReceiptNumber() + "");
                    txtNIC.setText(selectedReceipt.getStudent().getNic());
                    txtCourseId.setText(selectedReceipt.getStudent().getCourse().getCourseID());
                    txtBatchNumber.setText(selectedReceipt.getStudent().getBatchNumber()+"");
                    txtPaymentDescription.setText(selectedReceipt.getPaymentDescription());
                    txtPaymentMethod.setText(selectedReceipt.getPaymentMethod().toString());
                    txtOnlineReference.setText(selectedReceipt.getPaymentMethod() instanceof OnlinePayment ? ((OnlinePayment) selectedReceipt.getPaymentMethod()).getReferenceNumber() : "");
                    txtCardNumber.setText(selectedReceipt.getPaymentMethod() instanceof CardPayment ? ((CardPayment) selectedReceipt.getPaymentMethod()).getCardNumber() : "");
                    txtExpirationDate.setText(selectedReceipt.getPaymentMethod() instanceof CardPayment ? ((CardPayment) selectedReceipt.getPaymentMethod()).getCardExpireDate() + "" : "");
                    txtNameOnCard.setText(selectedReceipt.getPaymentMethod() instanceof CardPayment ? ((CardPayment) selectedReceipt.getPaymentMethod()).getNameOnCard() : "");
                    txtTotalAmount.setText(selectedReceipt.getStudent().getCourse().getCourseFee() + "");
                    txtAmountReceived.setText(selectedReceipt.getAmountReceived() + "");
                    txtBalanceAmount.setText(selectedReceipt.getBalancePayment() + "");
                    txtDueDate.setText(selectedReceipt.getDueDateOfBalancePayment() + "");
                    txtBalanceSettledDate.setText(selectedReceipt.getBalancePaymentReceipt() == null ? "" : selectedReceipt.getBalancePaymentReceipt().getPaymentDate() + "");
                    txtPaymentDate.setText(selectedReceipt.getPaymentDate() + "");
                    txtCashier.setText(selectedReceipt.getUser().getNameWithInitials());
                    txtNotes.setText(selectedReceipt.getNotes());
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong. Please contact developer").show();
                    txtSearch.requestFocus();
                }
            } else {
                clearAll();
                setEnableAll(false);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            loadAllReceipts(newValue);
        });

        loadAllReceipts("");
    }

    private void loadAllReceipts(String keyword) {
        tblResult.getItems().clear();

        for (Receipt receipt : receiptService.searchReceiptsByKeyword(keyword)) {
            tblResult.getItems().add(new ReceiptTM(receipt.getReceiptNumber(), receipt.getStudent().getCourse().getCourseID(), receipt.getPaymentDescription(), receipt.getStudent().getNic(), receipt.getUser().getNameWithInitials(), receipt.getReceiptDate(), receipt.getBalancePayment()));
        }
    }

    public void btnEdit_OnAction(ActionEvent actionEvent) {
        editReceipt();
    }

    public void btnEdit_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            editReceipt();
        }
    }

    private void editReceipt() {
        if (!txtNIC.isDisable()) {
            new Alert(Alert.AlertType.INFORMATION, "You are currently editing a receipt.\nPlease update or clear it and try again").show();
            btnUpdate.requestFocus();
            return;
        }

        if (txtNIC.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please select a row from table").show();
            tblResult.requestFocus();
            return;
        }
        setEnableAll(true);
        txtNIC.requestFocus();
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        updateReceipt();
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            updateReceipt();
        }
    }

    private void updateReceipt() {
        trimTextFields(txtNIC, txtNotes, txtDueDate, txtPaymentDate, txtAmountReceived, txtCardNumber, txtNameOnCard, txtExpirationDate, txtOnlineReference);

        if (!isValidated()) {
            return;
        }

        try {
            Receipt receiptToUpdate = receiptService.searchReceipt(Long.parseLong(txtReceiptNumber.getText()));

            String selectedPayment = txtPaymentDate.getText();
            PaymentMethod paymentMethod = null;
            switch (selectedPayment) {
                case "Cash":
                    paymentMethod = new CashPayment();
                    break;
                case "Online":
                    paymentMethod = new OnlinePayment(txtOnlineReference.getText());
                    break;
                case "Card":
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
                    Date date = formatter.parse(txtExpirationDate.getText());
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    paymentMethod = new CardPayment(txtCardNumber.getText(), localDate, txtNameOnCard.getText());
                    break;
            }

            Receipt receipt = new Receipt(Long.parseLong(txtReceiptNumber.getText()),
                    studentService.searchStudent(txtNIC.getText()),
                    txtPaymentDescription.getText(),
                    paymentMethod,
                    new BigDecimal(txtAmountReceived.getText()),
                    new BigDecimal(txtBalanceAmount.getText()),
                    LocalDate.parse(txtDueDate.getText()),
                    LocalDate.parse(txtPaymentDate.getText()),
                    txtNotes.getText(),
                    receiptToUpdate.getReceiptDate(),
                    receiptToUpdate.getUser(),
                    receiptToUpdate.getBalancePaymentReceipt());

            receiptService.updateReceipt(receipt);
            new Alert(Alert.AlertType.INFORMATION, "Payment have been updated successfully", ButtonType.OK).showAndWait();
            clearAll();
            setEnableAll(false);
            txtSearch.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtNIC.requestFocus();
        } catch (ParseException e) {
        }
    }

    private boolean isValidated() {

        if (!isValidNIC(txtNIC.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC", ButtonType.OK).show();
            txtNIC.requestFocus();
            return false;
        }

        try {
            studentService.searchStudent(txtNIC.getText());
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "There is no such a student for the given NIC", ButtonType.OK, ButtonType.CANCEL).show();
            return false;
        }

        if (txtPaymentMethod.getText().equals("Online") && !txtOnlineReference.getText().matches("[A-Za-z0-9.-]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid reference number", ButtonType.OK).show();
            txtOnlineReference.requestFocus();
            return false;
        } else if (txtPaymentMethod.getText().equals("Card")) {

            if (txtCardNumber.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Invalid card number", ButtonType.OK).show();
                txtCardNumber.requestFocus();
                return false;
            } else if (txtExpirationDate.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Card is expired", ButtonType.OK).show();
                txtExpirationDate.requestFocus();
                return false;
            } else if (txtNameOnCard.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Invalid name", ButtonType.OK).show();
                txtNameOnCard.requestFocus();
                return false;
            }

        } else if (!txtAmountReceived.getText().matches("((\\d{1,3}(,\\d{3})+)|\\d+)([.]\\d{1,2})?")) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount", ButtonType.OK).show();
            txtAmountReceived.requestFocus();
            return false;
        } else if (!isValidPastDate(txtDueDate.getText(), 14)) {
            new Alert(Alert.AlertType.ERROR, "Invalid due date for balance payment", ButtonType.OK).show();
            txtDueDate.requestFocus();
            return false;
        } else if (!isValidPastDate(txtPaymentDate.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid payment date", ButtonType.OK).show();
            txtPaymentDate.requestFocus();
            return false;
        }

        return true;
    }

    private void trimTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {
        clearAll();
        setEnableAll(false);
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            clearAll();
            setEnableAll(false);
        }
    }

    public void btnBalancePaymentReceipt_OnAction(ActionEvent actionEvent) {
        showBalancePaymentReceipt();
    }

    public void btnBalancePaymentReceipt_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            showBalancePaymentReceipt();
        }
    }

    private void showBalancePaymentReceipt() {
        if (txtReceiptNumber.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a receipt from table first", ButtonType.OK).show();
            return;
        }

        Receipt receipt;
        try {
            receipt = receiptService.searchReceipt(Long.parseLong(txtReceiptNumber.getText()));
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly went wrong. Please contact the developer", ButtonType.OK).show();
            btnBalancePaymentReceipt.requestFocus();
            return;
        }

        if (receipt.getBalancePaymentReceipt() == null) {
            new Alert(Alert.AlertType.ERROR, "There is no balance payment receipt to show", ButtonType.OK).show();
            btnBalancePaymentReceipt.requestFocus();
        }else{
            for (ReceiptTM item : tblResult.getItems()) {
                if (item.getReceiptNumber() == receipt.getReceiptNumber()) {
                    tblResult.getSelectionModel().select(item);
                    break;
                }
            }
        }
    }

    private void clearAll() {
        txtBalanceSettledDate.clear();
        txtPaymentDescription.clear();
        txtOnlineReference.clear();
        txtExpirationDate.clear();
        txtSearchFromDate.clear();
        txtAmountReceived.clear();
        txtReceiptNumber.clear();
        txtPaymentMethod.clear();
        txtBalanceAmount.clear();
        txtSearchToDate.clear();
        txtTotalAmount.clear();
        txtPaymentDate.clear();
        txtBatchNumber.clear();
        txtNameOnCard.clear();
        txtCardNumber.clear();
        txtCourseId.clear();
        txtDueDate.clear();
        txtCashier.clear();
        txtSearch.clear();
        txtNotes.clear();
        txtNIC.clear();
    }

    private void setEnableAll(boolean value) {
        btnBalancePaymentReceipt.setDisable(value);

        txtOnlineReference.setDisable(!value); // TODO: There should ne a condition to enable this fields
        txtExpirationDate.setDisable(!value);
        txtNameOnCard.setDisable(!value);
        txtCardNumber.setDisable(!value);

        txtAmountReceived.setDisable(!value);
        txtPaymentDate.setDisable(!value);
        txtDueDate.setDisable(!value);
        txtNotes.setDisable(!value);
        txtNIC.setDisable(!value);
    }
}
