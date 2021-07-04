package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Receipt;
import model.Student;
import model.User;
import model.sub.CardPayment;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import model.sub.PaymentMethod;
import service.ReceiptServiceRedisImpl;
import service.StudentServiceRedisImpl;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static util.ValidationUtil.isValidNIC;
import static util.ValidationUtil.isValidPastDate;

public class AddNewPaymentFormController {
    public final ReceiptServiceRedisImpl receiptService = new ReceiptServiceRedisImpl();
    public final StudentServiceRedisImpl studentService = new StudentServiceRedisImpl();
    private final BigDecimal registrationFee = new BigDecimal(5000);
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
    public JFXButton btnAddFile;
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
    private MainFormController mainFormController;
    private User loggedUser;
    private Student currentStudent;


    public void initialize() {
        MaterialUI.paintTextFields(txtNIC, txtReceiptNumber, txtOnlineReferenceNumber, txtFileName, txtCardNumber, txtExpirationDate, txtNameOnCard, txtAmountReceived, txtDueDateOfBalancePayment, txtPaymentDate, txtTotalAmount, txtBalanceAmount, txtNotes);

        txtNIC.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !txtNIC.getText().isEmpty()) {
                if (isValidNIC(txtNIC.getText())) {
                    try {
                        currentStudent = studentService.searchStudent(txtNIC.getText());
                        loadNicRelatedDetails();
                        loggedUser = (User) txtReceiptNumber.getScene().getWindow().getUserData();
                    } catch (NotFoundException e) {
                        clearNicRelatedDetails();
                        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "There is no such a student for the given NIC.\nDo you want to add this student?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

                        if (option.get() == ButtonType.CANCEL) {
                            txtNIC.requestFocus();
                        } else {
                            mainFormController.load(3, "Dashboard / Manage Students");
                            mainFormController.pneStage.setUserData(txtNIC.getText());
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid NIC").showAndWait();
                    clearNicRelatedDetails();
                }
            } else if (txtNIC.getText().isEmpty()) {
                clearNicRelatedDetails();
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

        rbnPaymentDescription.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (currentStudent != null) {
                loadPaymentDescriptionRelatedDetails();
            }

            if (((RadioButton) newValue).getText().equals("Custom Text")) {
                txtCustomText.setDisable(false);
                txtCustomText.requestFocus();
            } else {
                txtCustomText.setDisable(true);
            }
        });

        rbnPaymentMethod.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            txtOnlineReferenceNumber.setDisable(!rbnPaymentMethod.getToggles().get(1).isSelected());
            btnAddFile.setDisable(!rbnPaymentMethod.getToggles().get(1).isSelected());

            txtCardNumber.setDisable(!rbnPaymentMethod.getToggles().get(2).isSelected());
            txtExpirationDate.setDisable(!rbnPaymentMethod.getToggles().get(2).isSelected());
            txtNameOnCard.setDisable(!rbnPaymentMethod.getToggles().get(2).isSelected());
        });

        txtAmountReceived.textProperty().addListener((observable, oldValue, newValue) -> {
            setBalanceAmount();
        });

        Platform.runLater(() -> {
            txtReceiptNumber.setText(String.format("R%06d", receiptService.getLastReceiptNumber() + 1));
        });
    }

    private void loadNicRelatedDetails() {
        txtNameWithInitials.setText(currentStudent.getNameWithInitials());
        txtFullName.setText(currentStudent.getNameInFull());
        txtAddress.setText(currentStudent.getAddress());
        txtContactNumber.setText(currentStudent.getContactNumber());
        txtEmail.setText(currentStudent.getEmail());
        txtCourseId.setText(currentStudent.getCourse().getCourseID());
        txtBatchNumber.setText(currentStudent.getBatchNumber() + "");
        txtDiscount.setText(String.format("%.2f%%", currentStudent.getDiscount()));

        loadPaymentDescriptionRelatedDetails();
    }

    private void clearNicRelatedDetails() {
        currentStudent = null;
        txtNameWithInitials.clear();
        txtFullName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        txtCourseId.clear();
        txtBatchNumber.clear();
        txtDiscount.clear();

        txtTotalAmount.clear();
        txtBalanceAmount.clear();
    }

    private void loadPaymentDescriptionRelatedDetails() {
        List<Receipt> receipts = receiptService.searchReceiptsByStudent(currentStudent);
        String paymentDescription = ((RadioButton) rbnPaymentDescription.getSelectedToggle()).getText();
        double[] payments = {0, 0, 0}; // Registration fee, total payments, balance payments
        Receipt balancePaymentReceipt = null;

        for (Receipt receipt : receipts) { // TODO: Try to do these things using db
            if (receipt.getBalancePayment().compareTo(new BigDecimal(0)) > 0 && receipt.getBalancePaymentReceipt() == null) {
                payments[2] += receipt.getBalancePayment().doubleValue();
                balancePaymentReceipt = receipt;
            }
            if (receipt.getPaymentDescription().equals("Registration Fee")) {
                payments[0] += receipt.getAmountReceived().doubleValue();
            } else if (receipt.getPaymentDescription().equals("Installment") || receipt.getPaymentDescription().equals("Full Payment") || receipt.getPaymentDescription().equals("Balance Payment")) {
                payments[1] += receipt.getAmountReceived().doubleValue();
            }
        }

        switch (paymentDescription) {
            case "Registration Fee":
                if (payments[0] >= registrationFee.doubleValue()) {
                    new Alert(Alert.AlertType.INFORMATION, "It seems this student already pay the registration fee").showAndWait();
                }
                txtTotalAmount.setText(String.format("%,.2f", registrationFee));
                setBalanceAmount();
                break;
            case "Installment":
                if (payments[1] >= currentStudent.getCourse().getCourseFee()) {
                    new Alert(Alert.AlertType.INFORMATION, "It seems this student already pay the course fee").showAndWait();
                }
                txtTotalAmount.setText(String.format("%,.2f", currentStudent.getCourse().getCourseFee() / currentStudent.getCourse().getNumberOfInstallments()));
                setBalanceAmount();
                break;
            case "Full Payment":
                if (payments[1] >= currentStudent.getCourse().getCourseFee()) {
                    new Alert(Alert.AlertType.INFORMATION, "It seems this student already pay the course fee").showAndWait();
                }
                txtTotalAmount.setText(String.format("%,.2f", currentStudent.getCourse().getCourseFee() - payments[1]));
                setBalanceAmount();
                break;
            case "Balance Payment":
                if (payments[2] == 0) {
                    new Alert(Alert.AlertType.INFORMATION, "This student haven't any due balance payments").showAndWait();
                    txtTotalAmount.setText("0.00");
                    setBalanceAmount();
                } else {
                    txtTotalAmount.setText(String.format("%,.2f", balancePaymentReceipt.getBalancePayment()));
                    setBalanceAmount();
                }
                break;
            case "Custom Text":
                txtTotalAmount.setText("0.00");
                setBalanceAmount();
                break;
        }
    }

    private void setBalanceAmount() {
        if (txtAmountReceived.getText().isEmpty()) {
            txtBalanceAmount.setText(txtTotalAmount.getText());
        } else {
            BigDecimal totalAmount = new BigDecimal(txtTotalAmount.getText().replaceAll(",", ""));
            BigDecimal amountReceived = new BigDecimal(txtAmountReceived.getText().replaceAll(",", ""));

            if (totalAmount.compareTo(amountReceived) < 0) {
                txtBalanceAmount.setText("Invalid Input");
            } else {
                txtBalanceAmount.setText(String.format("%,.2f", totalAmount.subtract(amountReceived)));
            }
        }
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
                    new BigDecimal(txtAmountReceived.getText().replaceAll(",", "")),
                    new BigDecimal(txtBalanceAmount.getText().replaceAll(",", "")),
                    Double.parseDouble(txtBalanceAmount.getText().replaceAll(",", "")) > 0 ? LocalDate.parse(txtDueDateOfBalancePayment.getText()) : null,
                    LocalDate.parse(txtPaymentDate.getText()),
                    txtNotes.getText(),
                    LocalDate.now(),
                    loggedUser,
                    null);

            receiptService.addReceipt(receipt);
            new Alert(Alert.AlertType.INFORMATION, "Payment have been added successfully", ButtonType.OK).showAndWait();
            txtReceiptNumber.setText(String.format("R%06d", receiptService.getLastReceiptNumber() + 1));
            clearAll();
            txtNIC.requestFocus();
        } catch (DuplicateEntryException | NotFoundException e) {
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
        } else if (Double.parseDouble(txtBalanceAmount.getText().replaceAll(",", "")) > 0 && !isValidPastDate(txtDueDateOfBalancePayment.getText(), 14)) {
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

    public void lblArrowToFillData_OnMouseClicked(MouseEvent mouseEvent) {
        txtAmountReceived.setText(txtTotalAmount.getText());
    }

    public void btnAddFile_OnAction(ActionEvent actionEvent) {
    }

    public void btnAddFile_OnKeyPressed(KeyEvent keyEvent) {
    }
}
