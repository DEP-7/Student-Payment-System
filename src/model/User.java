package model;

import lk.ijse.crypto.DCCrypto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Serializable {
    private String nic;
    private String nameInFull;
    private String nameWithInitials;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String contactNumber;
    private String email;
    private boolean admin;
    private LocalDateTime lastLogin;
    private String username;
    private String password;
    private boolean accountActive;

    public User() {
    }

    public User(String nic, String nameInFull, String nameWithInitials, String gender, LocalDate dateOfBirth, String address, String contactNumber, String email, boolean admin, LocalDateTime lastLogin, String username, String password, boolean accountActive) {
        this.nic = nic;
        this.nameInFull = nameInFull;
        this.nameWithInitials = nameWithInitials;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.admin = admin;
        this.lastLogin = lastLogin;
        this.username = username;
        this.password = password;
        this.accountActive = accountActive;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNameInFull() {
        return nameInFull;
    }

    public void setNameInFull(String nameInFull) {
        this.nameInFull = nameInFull;
    }

    public String getNameWithInitials() {
        return nameWithInitials;
    }

    public void setNameWithInitials(String nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String passwordInput) {
        return password.equals(DCCrypto.encrypt(passwordInput, passwordInput.charAt(0) + "" + passwordInput.charAt(passwordInput.length()-1) + passwordInput.length()*2));
    }

    public void setPassword(String password) {
        this.password = DCCrypto.encrypt(password, password.charAt(0) + "" + password.charAt(password.length()-1) + password.length()*2);
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "nic='" + nic + '\'' +
                ", nameInFull='" + nameInFull + '\'' +
                ", nameWithInitials='" + nameWithInitials + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                ", lastLogin=" + lastLogin +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
