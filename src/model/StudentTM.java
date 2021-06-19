package model;

import java.io.Serializable;

public class StudentTM implements Serializable {
    private String nic;
    private String name;
    private String courseId;
    private int batchNumber;
    private String contactNumber;

    public StudentTM() {
    }

    public StudentTM(String nic, String name, String courseId, int batchNumber, String contactNumber) {
        this.nic = nic;
        this.name = name;
        this.courseId = courseId;
        this.batchNumber = batchNumber;
        this.contactNumber = contactNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
