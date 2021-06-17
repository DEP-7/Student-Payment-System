package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
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

    public User() {
    }

    public User(String nic, String nameInFull, String nameWithInitials, String gender, LocalDate dateOfBirth, String address, String contactNumber, String email, boolean admin, LocalDateTime lastLogin, String username, String password) {        this.nic = nic;
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
        return password.equals(passwordInput);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}