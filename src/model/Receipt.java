package model;

import model.sub.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Receipt {
    private long receiptNumber;
    private Student student;
    private String paymentDescription;
    private PaymentMethod paymentMethod;
    private BigDecimal amountReceived;
    private BigDecimal balancePayment;
    private LocalDate dueDateOfBalancePayment;
    private LocalDate paymentDate;
    private String notes;
    private LocalDate receiptDate;
    private User user;
    private Receipt balancePaymentReceipt;

    public Receipt() {
    }

    public Receipt(long receiptNumber, Student student, String paymentDescription, PaymentMethod paymentMethod, BigDecimal amountReceived, BigDecimal balancePayment, LocalDate dueDateOfBalancePayment, LocalDate paymentDate, String notes, LocalDate receiptDate, User user, Receipt balancePaymentReceipt) {
        this.receiptNumber = receiptNumber;
        this.student = student;
        this.paymentDescription = paymentDescription;
        this.paymentMethod = paymentMethod;
        this.amountReceived = amountReceived;
        this.balancePayment = balancePayment;
        this.dueDateOfBalancePayment = dueDateOfBalancePayment;
        this.paymentDate = paymentDate;
        this.notes = notes;
        this.receiptDate = receiptDate;
        this.user = user;
        this.balancePaymentReceipt = balancePaymentReceipt;
    }

    public long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public BigDecimal getBalancePayment() {
        return balancePayment;
    }

    public void setBalancePayment(BigDecimal balancePayment) {
        this.balancePayment = balancePayment;
    }

    public LocalDate getDueDateOfBalancePayment() {
        return dueDateOfBalancePayment;
    }

    public void setDueDateOfBalancePayment(LocalDate dueDateOfBalancePayment) {
        this.dueDateOfBalancePayment = dueDateOfBalancePayment;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Receipt getBalancePaymentReceipt() {
        return balancePaymentReceipt;
    }

    public void setBalancePaymentReceipt(Receipt balancePaymentReceipt) {
        this.balancePaymentReceipt = balancePaymentReceipt;
    }
}
