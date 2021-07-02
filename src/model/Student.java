package model;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import service.CourseService;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private Course course;
    private int batchNumber;
    private BigDecimal discount;

    public Student() {
    }

    public Student(String nic, String nameInFull, String nameWithInitials, String gender, LocalDate dateOfBirth, Image image, String eduQualification, String address, String contactNumber, String email, Course course, int batchNumber, BigDecimal discount) {
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
        this.course = course;
        this.batchNumber = batchNumber;
        this.discount = discount;
    }

    public static Student fromDB(String nic, Map<String, String> data) {
        try {
            return new Student(
                    nic,
                    data.get("nameInFull"),
                    data.get("nameWithInitials"),
                    data.get("gender"),
                    LocalDate.parse(data.get("dateOfBirth")),
                    null,//new Image(data.get("image")), // TODO: Learn and implement this...
                    data.get("eduQualification"),
                    data.get("address"),
                    data.get("contactNumber"),
                    data.get("email"),
                    new CourseService().searchCourse(data.get("course")),
                    Integer.parseInt(data.get("batchNumber")),
                    new BigDecimal(data.get("discount")));
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly gone wrong, please contact the developer").show();
            throw new RuntimeException("Saved value not exist in database");
        }
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public Map<String, String> toDB() {
        HashMap<String, String> map = new HashMap<>();
        map.put("nameInFull", nameInFull);
        map.put("nameWithInitials", nameWithInitials);
        map.put("gender", gender);
        map.put("dateOfBirth", dateOfBirth + "");
        map.put("image", image + ""); // TODO: How to do this?
        map.put("eduQualification", eduQualification);
        map.put("address", address);
        map.put("contactNumber", contactNumber);
        map.put("email", email);
        map.put("course", course.getCourseID());
        map.put("batchNumber", batchNumber + "");
        map.put("discount", discount + "");
        return map;
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
                ", courseId='" + course + '\'' +
                ", batchNumber=" + batchNumber +
                ", discount=" + discount +
                '}';
    }
}
