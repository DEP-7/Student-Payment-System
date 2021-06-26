package model;

import java.time.LocalDate;

public class Receipt {
    private long receiptNumber;
    private Student student;
    private String paymentDescription;
    private PaymentMethod paymentMethod; // TODO: Check this with sir
    private double amountReceived;
    private LocalDate dueDateOfBalancePayment;
    private LocalDate paymentDate;
    private String notes;
    private LocalDate receiptDate;
    private User userId;

    public Receipt() {
    }

    public Receipt(long receiptNumber, Student student, String paymentDescription, PaymentMethod paymentMethod, double amountReceived, LocalDate dueDateOfBalancePayment, LocalDate paymentDate, String notes, LocalDate receiptDate, User userId) {
        this.receiptNumber = receiptNumber;
        this.student = student;
        this.paymentDescription = paymentDescription;
        this.paymentMethod = paymentMethod;
        this.amountReceived = amountReceived;
        this.dueDateOfBalancePayment = dueDateOfBalancePayment;
        this.paymentDate = paymentDate;
        this.notes = notes;
        this.receiptDate = receiptDate;
        this.userId = userId;
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

    public double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}

class PaymentMethod {
    private PaymentMethod() {
    }

    static class Cash {
        @Override
        public boolean equals(Object obj) {
            return this.hashCode()== obj.hashCode();
        }
    }

    static class Online {
        private String onlineReferenceNumber;
        private String fileName;

        public Online(String onlineReferenceNumber, String fileName) {
            this.onlineReferenceNumber = onlineReferenceNumber;
            this.fileName = fileName;
        }

        public String getOnlineReferenceNumber() {
            return onlineReferenceNumber;
        }

        public void setOnlineReferenceNumber(String onlineReferenceNumber) {
            this.onlineReferenceNumber = onlineReferenceNumber;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public int hashCode() {
            return onlineReferenceNumber.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.hashCode() == o.hashCode();
        }
    }

    static class Card {
        private String cardNumber;
        private LocalDate cardExpDate;
        private String nameOnCard;

        public Card(String cardNumber, LocalDate cardExpDate, String nameOnCard) {
            this.cardNumber = cardNumber;
            this.cardExpDate = cardExpDate;
            this.nameOnCard = nameOnCard;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public LocalDate getCardExpDate() {
            return cardExpDate;
        }

        public void setCardExpDate(LocalDate cardExpDate) {
            this.cardExpDate = cardExpDate;
        }

        public String getNameOnCard() {
            return nameOnCard;
        }

        public void setNameOnCard(String nameOnCard) {
            this.nameOnCard = nameOnCard;
        }

        @Override
        public int hashCode() {
            return cardNumber.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.hashCode() == o.hashCode();
        }
    }
}