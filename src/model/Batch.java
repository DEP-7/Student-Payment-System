package model;

import java.time.LocalDate;

public class Batch {
    private int batchNumber;
    private String courseId;
    private LocalDate startedDate;
    private LocalDate endDate;
    private String notes;

    public Batch() {
    }

    public Batch(int batchNumber, String courseId, LocalDate startedDate, LocalDate endDate, String notes) {
        this.batchNumber = batchNumber;
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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
                ", courseId='" + courseId + '\'' +
                ", startedDate=" + startedDate +
                ", endDate=" + endDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
