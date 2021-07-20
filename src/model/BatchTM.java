/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package model;

import java.io.Serializable;
import java.time.LocalDate;

public class BatchTM implements Serializable {
    private int batchNumber;
    private int numberOfStudents;
    private LocalDate startedDate;
    private LocalDate planedEndDate;
    private LocalDate endDate;
    private Course course;

    public BatchTM() {
    }

    public BatchTM(int batchNumber, int numberOfStudents, LocalDate startedDate, LocalDate planedEndDate, LocalDate endDate, Course course) {
        this.batchNumber = batchNumber;
        this.numberOfStudents = numberOfStudents;
        this.startedDate = startedDate;
        this.planedEndDate = planedEndDate;
        this.endDate = endDate;
        this.course = course;
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
                "batchNumber=" + getBatchNumber() +
                ", numberOfStudents=" + getNumberOfStudents() +
                ", startedDate=" + getStartedDate() +
                ", planedEndDate=" + getPlanedEndDate() +
                ", endDate=" + getEndDate() +
                '}';
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
