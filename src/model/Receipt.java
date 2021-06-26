package model;

import java.time.LocalDate;

public class Receipt {
    long receiptNumber;
    Student student;
    String paymentDescription;
    String paymentMethod;
    double amountReceived;
    LocalDate dueDateOfBalancePayment;
    LocalDate paymentDate;
    String notes;
    LocalDate receiptDate;
    User userId;
}
