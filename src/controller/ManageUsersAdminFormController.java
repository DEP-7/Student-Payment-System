package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;
import model.UserTM;
import service.UserService;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.MaterialUI;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static util.ValidationUtil.*;

public class ManageUsersAdminFormController {
    private final UserService userService = new UserService();
    private boolean isFirstFocusLostFromNIC = true;
    private String userNICToUpdate = "";
    public TableView<UserTM> tblResult;
    public JFXCheckBox chkMakeAdmin;
    public ToggleGroup rbnGender;
    public TextField txtNameWithInitials;
    public TextField txtContactNumber;
    public TextField txtDateOfBirth;
    public TextField txtFullName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtEmail;
    public TextField txtNIC;
    public TextField txtAge;
    public JFXButton btnResetPassword;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnEdit;
    public JFXButton btnAdd;
    public Label lblDefaultUserName;
    public Label lblDefaultPassword;

    public void initialize() {
        MaterialUI.paintTextFields(txtNameWithInitials, txtContactNumber, txtDateOfBirth, txtFullName, txtAddress, txtSearch, txtEmail, txtNIC, txtAge);

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("nic"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("gender"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("accountStatus"));
        tblResult.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("lastLogin"));

        txtDateOfBirth.textProperty().addListener((observable, oldValue, newValue) -> {
            String inputText = txtDateOfBirth.getText();

            if (inputText.length() == 4 || inputText.length() == 7 || inputText.length() == 10) {
                LocalDate today = LocalDate.now();
                Period period = null;
                LocalDate dob;

                try {
                    if (inputText.length() == 4) {
                        inputText = inputText + "-" + String.format("%02d", today.getMonthValue()) + "-" + today.getDayOfMonth();
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    } else if (inputText.length() == 7) {
                        inputText = inputText + "-" + today.getDayOfMonth();
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    } else if (inputText.length() == 10) {
                        dob = LocalDate.parse(inputText);
                        period = Period.between(dob, today);
                    }

                    if (period.getYears() >= 0 && period.getMonths() >= 0 && period.getDays() >= 0) {
                        txtAge.setText(period.getYears() + "-" + period.getMonths() + "-" + period.getDays());
                    } else {
                        txtAge.setText("Invalid");
                    }

                } catch (DateTimeParseException e) {
                    txtAge.setText("Invalid");
                }
            } else if (inputText.length() < 4) {
                txtAge.setText("00-00-00");
            }
        });

        txtNIC.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                autoFillDataUsingNIC();
                String nic = txtNIC.getText().trim();
                if (!isFirstFocusLostFromNIC && isValidNIC(nic)) {
                    lblDefaultUserName.setText("System generated username is \""+ nic +"\" and it is recommended to change the username after first login");
                    lblDefaultPassword.setText("System generated password is \""+ nic +"\" and it is recommended to change the password after first login");
                }
            }

            if (!newValue && isFirstFocusLostFromNIC) {
                if (isValidNIC(txtNIC.getText())) {
                    try {
                        User existingUser = userService.searchUser(txtNIC.getText());
                        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "User exist. Do you want to view details?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                        if (option.get() == ButtonType.CANCEL) {
                            txtNIC.clear();
                            txtNIC.requestFocus();
                            return;
                        }
                        txtFullName.setText(existingUser.getNameInFull());
                        txtNameWithInitials.setText(existingUser.getNameWithInitials());
                        rbnGender.getToggles().get(0).setSelected((existingUser.getGender() == "Male"));
                        txtDateOfBirth.setText(existingUser.getDateOfBirth().toString());
                        txtAddress.setText(existingUser.getAddress());
                        txtContactNumber.setText(existingUser.getContactNumber());
                        txtEmail.setText(existingUser.getEmail());
                        setDisableAll(true,false);
                        isFirstFocusLostFromNIC = false;
                    } catch (NotFoundException e) {
                    }
                }
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllUsers(newValue);
        });

        loadAllUsers("");
    }

    private void loadAllUsers(String keyword) {
        tblResult.getItems().clear();

        for (User user : userService.searchUsersByKeyword(keyword)) {
            if (user.isAccountActive()) {
                tblResult.getItems().add(new UserTM(user.getNic(), user.getNameWithInitials(), user.getGender(), user.isAdmin()?"Admin":"General User", user.getLastLogin()));
            }

        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        addUser(false);
    }

    public void btnAdd_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addUser(false);
        }
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        addUser(true);
        btnAdd.setDisable(true);
    }

    public void btnUpdate_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            addUser(true);
            btnAdd.setDisable(true);
        }
    }

    private void addUser(boolean isUpdateUser) {
        trimTextFields(txtAge, txtNIC, txtEmail, txtSearch, txtAddress, txtFullName, txtDateOfBirth,txtContactNumber,txtNameWithInitials);

        if (!isValidated()) {
            return;
        }

        User user = new User(txtNIC.getText(),
                txtFullName.getText(),
                txtNameWithInitials.getText(),
                ((RadioButton) (rbnGender.getSelectedToggle())).getText(),
                LocalDate.parse(txtDateOfBirth.getText()),
                txtAddress.getText(),
                txtContactNumber.getText(),
                txtEmail.getText(),
                chkMakeAdmin.isSelected(),
                null,
                txtNIC.getText(),
                txtNIC.getText(),
                true);
        try {
            if (isUpdateUser) {
                userService.updateUser(user, userNICToUpdate);
            } else {
                userService.addUser(user);
            }
            loadAllUsers(txtSearch.getText());
            String alertMessage = isUpdateUser ? "User have been updated successfully" : "User have been added successfully";
            new Alert(Alert.AlertType.INFORMATION, alertMessage, ButtonType.OK).show();
            clearAll();
            txtNIC.requestFocus();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "User already exist for this NIC " + txtNIC.getText()).show();
            txtNIC.requestFocus();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            txtNIC.requestFocus();
        }
    }

    private void trimTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText(textField.getText().trim());
        }
    }

    private boolean isValidated() {

        if (!isValidNIC(txtNIC.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC", ButtonType.OK).show();
            txtNIC.requestFocus();
            return false;
        } else if (!txtFullName.getText().matches("[A-Za-z ]{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid full name", ButtonType.OK).show();
            txtFullName.requestFocus();
            return false;
        } else if (!txtNameWithInitials.getText().matches("[A-Za-z .]{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name", ButtonType.OK).show();
            txtNameWithInitials.requestFocus();
            return false;
        } else if (!isValidPastDate(txtDateOfBirth.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth", ButtonType.OK).show();
            txtDateOfBirth.requestFocus();
            return false;
        } else if (!txtAddress.getText().matches("([^\\W_]|[-.,/\\\\ ]){4,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid address", ButtonType.OK).show();
            txtAddress.requestFocus();
            return false;
        } else if (!txtContactNumber.getText().matches("[0]\\d{2}-\\d{7}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number", ButtonType.OK).show();
            txtContactNumber.requestFocus();
            return false;
        } else if (!isValidEmail(txtEmail.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address", ButtonType.OK).show();
            txtEmail.requestFocus();
            return false;
        }

        return true;
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
        chkMakeAdmin.setSelected(false);

        rbnGender.getToggles().get(0).setSelected(true);

        txtNIC.clear();
        txtFullName.clear();
        txtNameWithInitials.clear();
        txtDateOfBirth.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        txtAge.setText("00-00-00");

        lblDefaultUserName.setText("System generated username is nic and it is recommended to change the username after first login");
        lblDefaultPassword.setText("System generated password is nic and it is recommended to change the password after first login");

        isFirstFocusLostFromNIC = true;
        setDisableAll(false);
    }

    private void setDisableAll(boolean value, boolean disableAddButton) {
        ((RadioButton)rbnGender.getToggles().get(0)).getParent().setDisable(value);

        txtNIC.setDisable(value);
        txtFullName.setDisable(value);
        txtNameWithInitials.setDisable(value);
        txtDateOfBirth.setDisable(value);
        txtAge.setDisable(value);
        txtAddress.setDisable(value);
        txtContactNumber.setDisable(value);
        txtEmail.setDisable(value);

        chkMakeAdmin.setDisable(value);

        btnAdd.setDisable(value);
        btnUpdate.setDisable(value);
        btnEdit.setDisable(!value);
        btnDelete.setDisable(!value);
        btnResetPassword.setDisable(!value);

        userNICToUpdate = value ? "" : txtNIC.getText();
    }

    private void setDisableAll(boolean value) {
        setDisableAll(value,value);
    }

    public void btnEdit_OnAction(ActionEvent actionEvent) {
        setDisableAll(false);
    }

    public void btnEdit_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            setDisableAll(false);
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        try {
            userService.deleteUser(txtNIC.getText());
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            btnClear.requestFocus();
        }
    }

    public void btnDelete_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            try {
                userService.deleteUser(txtNIC.getText());
            } catch (NotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
                btnClear.requestFocus();
            }
        }
    }

    public void btnResetPassword_OnAction(ActionEvent actionEvent) {
        try {
            String nic = txtNIC.getText().trim();
            userService.resetAccount(nic);
            lblDefaultUserName.setText("System generated username is \""+ nic +"\" and it is recommended to change the username after first login");
            lblDefaultPassword.setText("System generated password is \""+ nic +"\" and it is recommended to change the password after first login");
            btnResetPassword.setDisable(true);
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
            btnClear.requestFocus();
        }
    }

    public void btnResetPassword_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {
            try {
                String nic = txtNIC.getText().trim();
                userService.resetAccount(nic);
                lblDefaultUserName.setText("System generated username is \""+ nic +"\" and it is recommended to change the username after first login");
                lblDefaultPassword.setText("System generated password is \""+ nic +"\" and it is recommended to change the password after first login");
                btnResetPassword.setDisable(true);
            } catch (NotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something terribly wrong. Please contact DC").show();
                btnClear.requestFocus();
            }
        }
    }

    private void autoFillDataUsingNIC() {
        if (!isValidNIC(txtNIC.getText())) {
            return;
        }
        String nic = txtNIC.getText().trim();
        if (nic.length() == 10) {
            nic = "19" + nic;
        }
        LocalDate dob = LocalDate.parse(nic.substring(0, 4) + "-01-01");
        int days = Integer.parseInt(nic.substring(4, 7));
        if (days > 500) {
            rbnGender.getToggles().get(0).setSelected(false);
        } else {
            rbnGender.getToggles().get(0).setSelected(true);
        }
        days = days > 500 ? days - 500 : days;
        dob = dob.plusDays(days - 2);
        txtDateOfBirth.setText(dob.toString());
    }
}
