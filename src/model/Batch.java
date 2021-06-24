package model;

import java.time.LocalDate;

public class Batch {
    private int batchNumber;
    private Course course;
    private LocalDate startedDate;
    private LocalDate endDate;
    private String notes;

    public Batch() {
    }

    public Batch(int batchNumber, Course course, LocalDate startedDate, LocalDate endDate, String notes) {
        this.batchNumber = batchNumber;
        this.course = course;
        this.startedDate = startedDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchNumber=" + batchNumber +
                ", course=" + course +
                ", startedDate=" + startedDate +
                ", endDate=" + endDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
