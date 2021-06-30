package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DashboardTM implements Serializable {
    private String courseId;
    private int newRegistrations;
    private int numberOfPayments;
    private BigDecimal totalIncome;

    public DashboardTM() {
    }

    public DashboardTM(String courseId, int newRegistrations, int numberOfPayments, BigDecimal totalIncome) {
        this.courseId = courseId;
        this.newRegistrations = newRegistrations;
        this.numberOfPayments = numberOfPayments;
        this.totalIncome = totalIncome;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getNewRegistrations() {
        return newRegistrations;
    }

    public void setNewRegistrations(int newRegistrations) {
        this.newRegistrations = newRegistrations;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public void setNumberOfPayments(int numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "DashboardTM{" +
                "courseId='" + courseId + '\'' +
                ", newRegistrations=" + newRegistrations +
                ", numberOfPayments=" + numberOfPayments +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
