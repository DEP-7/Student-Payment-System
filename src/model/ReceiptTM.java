/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceiptTM implements Serializable {

    private long receiptNumber;
    private String courseId;
    private String paymentDescription;
    private String studentId;
    private String userName;
    private LocalDate receiptDate;
    private BigDecimal balancePayment;

    public ReceiptTM() {
    }

    public ReceiptTM(long receiptNumber, String courseId, String paymentDescription, String studentId, String userName, LocalDate receiptDate, BigDecimal balancePayment) {
        this.receiptNumber = receiptNumber;
        this.courseId = courseId;
        this.paymentDescription = paymentDescription;
        this.studentId = studentId;
        this.userName = userName;
        this.receiptDate = receiptDate;
        this.balancePayment = balancePayment;
    }

    public long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigDecimal getBalancePayment() {
        return balancePayment;
    }

    public void setBalancePayment(BigDecimal balancePayment) {
        this.balancePayment = balancePayment;
    }

    @Override
    public String toString() {
        return "ReceiptTM{" +
                "receiptNumber=" + receiptNumber +
                ", courseId='" + courseId + '\'' +
                ", paymentDescription='" + paymentDescription + '\'' +
                ", studentId='" + studentId + '\'' +
                ", userName='" + userName + '\'' +
                ", paymentDate=" + receiptDate +
                ", balancePayment=" + balancePayment +
                '}';
    }
}
