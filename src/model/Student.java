package model;

import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Student {
    private String nic;
    private String nameInFull;
    private String nameWithInitials;
    private String gender;
    private LocalDate dateOfBirth;
    private Image image;
    private String eduQualification;
    private String address;
    private String contactNumber;
    private String email;
    private String courseId;
    private int batchNumber;
    private BigDecimal discount;

    public Student() {
    }

    public Student(String nic, String nameInFull, String nameWithInitials, String gender, LocalDate dateOfBirth, Image image, String eduQualification, String address, String contactNumber, String email, String courseId, int batchNumber, BigDecimal discount) {
        this.nic = nic;
        this.nameInFull = nameInFull;
        this.nameWithInitials = nameWithInitials;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.eduQualification = eduQualification;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.courseId = courseId;
        this.batchNumber = batchNumber;
        this.discount = discount;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getEduQualification() {
        return eduQualification;
    }

    public void setEduQualification(String eduQualification) {
        this.eduQualification = eduQualification;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nic='" + nic + '\'' +
                ", nameInFull='" + nameInFull + '\'' +
                ", nameWithInitials='" + nameWithInitials + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", image=" + image +
                ", eduQualification='" + eduQualification + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", courseId='" + courseId + '\'' +
                ", batchNumber=" + batchNumber +
                ", discount=" + discount +
                '}';
    }
}
