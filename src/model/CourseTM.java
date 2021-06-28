package model;

import java.io.Serializable;
import java.time.LocalDate;

public class CourseTM implements Serializable {
    private String courseID;
    private String courseName;
    private double courseFee;
    private int numberOfInstallments;
    private String duration;
    private String courseStatus;
    private LocalDate courseInitiationDate;

    public CourseTM() {
    }

    public CourseTM(String courseID, String courseName, double courseFee, int numberOfInstallments, String duration, String courseStatus, LocalDate courseInitiationDate) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.numberOfInstallments = numberOfInstallments;
        this.duration = duration;
        this.courseStatus = courseStatus;
        this.courseInitiationDate = courseInitiationDate;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public LocalDate getCourseInitiationDate() {
        return courseInitiationDate;
    }

    public void setCourseInitiationDate(LocalDate courseInitiationDate) {
        this.courseInitiationDate = courseInitiationDate;
    }

    @Override
    public String toString() {
        return "CourseTM{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseFee=" + courseFee +
                ", numberOfInstallments=" + numberOfInstallments +
                ", duration='" + duration + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseInitiationDate=" + courseInitiationDate +
                '}';
    }
}
