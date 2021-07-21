package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Course implements Serializable {
    private String courseID;
    private String courseName;
    private double courseFee;
    private int numberOfInstallments;
    private int numberOfStudents;
    private String duration;
    private String firstInstallment;
    private String installmentGap;
    private String courseStatus;
    private String courseSchedule;
    private String minimumRequirements;
    private String notes;
    private LocalDate courseInitiationDate;

    public Course() {
    }

    public Course(String courseID, String courseName, double courseFee, int numberOfInstallments, int numberOfStudents, String duration, String firstInstallment, String installmentGap, String courseStatus, String courseSchedule, String minimumRequirements, String notes, LocalDate courseInitiationDate) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.numberOfInstallments = numberOfInstallments;
        this.numberOfStudents = numberOfStudents;
        this.duration = duration;
        this.firstInstallment = firstInstallment;
        this.installmentGap = installmentGap;
        this.courseStatus = courseStatus;
        this.courseSchedule = courseSchedule;
        this.minimumRequirements = minimumRequirements;
        this.notes = notes;
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

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public String getInstallmentGap() {
        return installmentGap;
    }

    public void setInstallmentGap(String installmentGap) {
        this.installmentGap = installmentGap;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(String courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public String getMinimumRequirements() {
        return minimumRequirements;
    }

    public void setMinimumRequirements(String minimumRequirements) {
        this.minimumRequirements = minimumRequirements;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCourseInitiationDate() {
        return courseInitiationDate;
    }

    public void setCourseInitiationDate(LocalDate courseInitiationDate) {
        this.courseInitiationDate = courseInitiationDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseFee=" + courseFee +
                ", numberOfInstallments=" + numberOfInstallments +
                ", numberOfStudents=" + numberOfStudents +
                ", duration='" + duration + '\'' +
                ", firstInstallment='" + firstInstallment + '\'' +
                ", installmentGap='" + installmentGap + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseSchedule='" + courseSchedule + '\'' +
                ", minimumRequirements='" + minimumRequirements + '\'' +
                ", notes='" + notes + '\'' +
                ", courseInitiationDate=" + courseInitiationDate +
                '}';
    }
}
