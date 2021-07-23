package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Receipt;
import model.User;
import model.sub.CardPayment;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import model.sub.PaymentMethod;
import service.ReceiptService;
import service.StudentService;
import service.UserService;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static util.ValidationUtil.isValidNIC;
import static util.ValidationUtil.isValidPastDate;

public class AddNewPaymentFormController {
    public final ReceiptService receiptService = new ReceiptService();
    public final StudentService studentService = new StudentService();
    public final UserService userService = new UserService();
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
    public JFXButton btnMakePayment;
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
    public Label lblBalancePaymentReceipt;
    public Label lblMaximumDueDate;

    MainFormController mainFormController;
    User loggedUser;

    public void initialize() {
        MaterialUI.paintTextFields(txtNIC, txtReceiptNumber, txtOnlineReferenceNumber, txtFileName, txtCardNumber, txtExpirationDate, txtNameOnCard, txtAmountReceived, txtDueDateOfBalancePayment, txtPaymentDate, txtTotalAmount, txtBalanceAmount, txtNotes);

        txtNIC.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !txtNIC.getText().isEmpty()) {
                if (isValidNIC(txtNIC.getText())) {
                    try {
                        studentService.searchStudent(txtNIC.getText());
                    } catch (NotFoundException e) {
                        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "There is no such a student for the given NIC.\nDo you want to add this student?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

                        if (option.get() == ButtonType.CANCEL) {
                            txtNIC.requestFocus();
                        } else {
                            loggedUser = (User) txtNIC.getScene().getWindow().getUserData();
                            mainFormController.load(3, "Dashboard / Manage Students");
                            mainFormController.pneStage.setUserData(txtNIC.getText());
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid NIC").show();
                }
            }
        });

        btnMakePayment.getParent().parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    mainFormController = (MainFormController) txtNIC.getScene().getUserData();
                    mainFormController.pneStage.setUserData(null);
                });
            }
        });

        Platform.runLater(() -> {
            txtReceiptNumber.setText(String.format("R%06d",receiptService.getLastReceiptNumber()+1));
        });
    }

    public void btnMakePayment_OnAction(ActionEvent actionEvent) {
        makePayment();
    }

    public void btnMakePayment_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            makePayment();
        }
    }

    private void makePayment() {
        trimTextFields(txtNIC, txtCustomText, txtOnlineReferenceNumber, txtCardNumber, txtExpirationDate, txtNameOnCard, txtAmountReceived);

        if (!isValidated()) {
            return;
        }

        try {
            int selectedToggleIndex = rbnPaymentMethod.getToggles().indexOf(rbnPaymentMethod.getSelectedToggle());
            PaymentMethod paymentMethod = null;
            switch (selectedToggleIndex) {
                case 0:
                    paymentMethod = new CashPayment();
                    break;
                case 1:
                    paymentMethod = new OnlinePayment(txtOnlineReferenceNumber.getText());
                    break;
                case 2:
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
                    Date date = formatter.parse(txtExpirationDate.getText());
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    paymentMethod = new CardPayment(txtCardNumber.getText(), localDate, txtNameOnCard.getText());
                    break;
            }

            Receipt receipt = new Receipt(Long.parseLong(txtReceiptNumber.getText().substring(1)),
                    studentService.searchStudent(txtNIC.getText()),
                    ((RadioButton) rbnPaymentDescription.getSelectedToggle()).getText() + (rbnPaymentDescription.getToggles().get(4).isSelected() ? txtCustomText.getText() : ""),
                    paymentMethod,
                    new BigDecimal(txtAmountReceived.getText()),
                    new BigDecimal(txtBalanceAmount.getText()),
                    LocalDate.parse(txtDueDateOfBalancePayment.getText()),
                    LocalDate.parse(txtPaymentDate.getText()),
                    txtNotes.getText(),
                    LocalDate.now(),
                    loggedUser,
                    null);

            receiptService.addReceipt(receipt);
            new Alert(Alert.AlertType.INFORMATION, "Payment have been added successfully", ButtonType.OK).showAndWait();
            txtReceiptNumber.setText(String.format("RO6%d", receiptService.getLastReceiptNumber() + 1));
            clearAll();
            txtNIC.requestFocus();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtNIC.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtNIC.requestFocus();
        } catch (ParseException e) {
        } catch (FailedOperationException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to sync the data with the database, try again").show();
            txtNIC.requestFocus();
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
            Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "There is no such a student for the given NIC.\nDo you want to add this student?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

            if (option.get() == ButtonType.CANCEL) {
                txtNIC.requestFocus();
            } else {
                mainFormController.load(3, "Dashboard / Manage Students");
                mainFormController.pneStage.setUserData(txtNIC.getText());
            }
            return false;
        }

        if (rbnPaymentDescription.getToggles().get(4).isSelected() && txtCustomText.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a custom text", ButtonType.OK).show();
            txtCustomText.requestFocus();
            return false;
        } else if (rbnPaymentMethod.getToggles().get(1).isSelected() && !txtOnlineReferenceNumber.getText().matches("[A-Za-z0-9.-]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid reference number", ButtonType.OK).show();
            txtOnlineReferenceNumber.requestFocus();
            return false;
        } else if (rbnPaymentMethod.getToggles().get(2).isSelected()) {

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
        } else if (!isValidPastDate(txtDueDateOfBalancePayment.getText(), 14)) {
            new Alert(Alert.AlertType.ERROR, "Invalid due date", ButtonType.OK).show();
            txtDueDateOfBalancePayment.requestFocus();
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
    }

    public void btnClear_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            clearAll();
        }
    }

    private void clearAll() {
        rbnPaymentMethod.getToggles().get(0).setSelected(true);
        rbnPaymentDescription.getToggles().get(0).setSelected(true);

        txtNIC.clear();
        txtCustomText.clear();
        txtOnlineReferenceNumber.clear();
        txtFileName.clear();
        txtCardNumber.clear();
        txtExpirationDate.clear();
        txtNameOnCard.clear();
        txtAmountReceived.clear();
        txtTotalAmount.clear();
        txtDueDateOfBalancePayment.clear();
        txtBalanceAmount.clear();
        txtPaymentDate.clear();
        txtNotes.clear();
        txtNameWithInitials.clear();
        txtContactNumber.clear();
        txtBatchNumber.clear();
        txtFullName.clear();
        txtCourseId.clear();
        txtDiscount.clear();
        txtAddress.clear();
        txtEmail.clear();

        lblBalancePaymentReceipt.setText("");
        lblMaximumDueDate.setText("");

        imgMasterCard.setVisible(true);
        imgVisaCard.setVisible(true);
    }

    public void btnAddFile_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddFile_OnKeyPressed(KeyEvent keyEvent) {
    }
}
