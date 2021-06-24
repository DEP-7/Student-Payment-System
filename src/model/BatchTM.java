package model;

import java.io.Serializable;
import java.time.LocalDate;

public class BatchTM implements Serializable {
    private int batchNumber;
    private int numberOfStudents;
    private LocalDate startedDate;
    private LocalDate planedEndDate;
    private LocalDate endDate;

    public BatchTM() {
    }

    public BatchTM(int batchNumber, int numberOfStudents, LocalDate startedDate, LocalDate planedEndDate, LocalDate endDate) {
        this.batchNumber = batchNumber;
        this.numberOfStudents = numberOfStudents;
        this.startedDate = startedDate;
        this.planedEndDate = planedEndDate;
        this.endDate = endDate;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDate getPlanedEndDate() {
        return planedEndDate;
    }

    public void setPlanedEndDate(LocalDate planedEndDate) {
        this.planedEndDate = planedEndDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BatchTM{" +
                "batchNumber=" + batchNumber +
                ", numberOfStudents=" + numberOfStudents +
                ", startedDate=" + startedDate +
                ", planedEndDate=" + planedEndDate +
                ", endDate=" + endDate +
                '}';
    }
}
