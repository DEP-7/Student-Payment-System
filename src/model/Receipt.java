package model;

import javafx.scene.control.Alert;
import model.sub.CardPayment;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import model.sub.PaymentMethod;
import service.ReceiptServiceRedisImpl;
import service.StudentServiceRedisImpl;
import service.UserServiceRedisImpl;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Receipt {
    private static final String PAYMENT_METHOD_DIVIDER = "#*/@#";
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

    public static Receipt fromMap(String receiptNumber, Map<String, String> data) {
        String paymentMethodInDB = data.get("paymentMethod");
        PaymentMethod paymentMethod = null;

        try {

            if (paymentMethodInDB.startsWith("Cash")) {
                paymentMethod = new CashPayment();
            } else if (paymentMethodInDB.startsWith("Online")) {
                String[] details = paymentMethodInDB.split(PAYMENT_METHOD_DIVIDER);
                paymentMethod = new OnlinePayment(details[1], details[2]);
            } else if (paymentMethodInDB.startsWith("Card")) {
                String[] details = paymentMethodInDB.split(PAYMENT_METHOD_DIVIDER);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
                Date date = formatter.parse(details[2]);
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                paymentMethod = new CardPayment(details[1], localDate, details[3]);
            }

            return new Receipt(
                    Long.parseLong(receiptNumber),
                    new StudentServiceRedisImpl().searchStudent(data.get("student")),
                    data.get("paymentDescription"),
                    paymentMethod,
                    new BigDecimal(data.get("amountReceived")),
                    new BigDecimal(data.get("balancePayment")),
                    data.get("dueDateOfBalancePayment").equals("null") ? null : LocalDate.parse(data.get("dueDateOfBalancePayment")),
                    LocalDate.parse(data.get("paymentDate")),
                    data.get("notes"),
                    LocalDate.parse(data.get("receiptDate")),
                    new UserServiceRedisImpl().searchUser(data.get("user")),
                    data.get("balancePaymentReceipt").equals("null") ? null : new ReceiptServiceRedisImpl().searchReceipt(Long.parseLong(data.get("balancePaymentReceipt"))));
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly gone wrong, please contact the developer").show();
            throw new RuntimeException("Saved value not exist in database");
        } catch (ParseException e) {
            new Alert(Alert.AlertType.ERROR, "Something terribly gone wrong, please contact the developer").show();
            throw new RuntimeException("Card expiration date parse exception");
        }
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

    public Map<String, String> toMap() {
        String paymentMethodToSaveInDB = "";

        if (paymentMethod.toString().equals("Cash Payment")) {
            paymentMethodToSaveInDB = "Cash";
        } else if (paymentMethod.toString().equals("Online Payment")) {
            paymentMethodToSaveInDB = "Online" + PAYMENT_METHOD_DIVIDER + ((OnlinePayment) paymentMethod).getReferenceNumber() + PAYMENT_METHOD_DIVIDER + ((OnlinePayment) paymentMethod).getFileName();
        } else if (paymentMethod.toString().equals("Card Payment")) {
            paymentMethodToSaveInDB = "Card" + PAYMENT_METHOD_DIVIDER + ((CardPayment) paymentMethod).getCardNumber() + PAYMENT_METHOD_DIVIDER + ((CardPayment) paymentMethod).getCardExpireDate() + PAYMENT_METHOD_DIVIDER + ((CardPayment) paymentMethod).getNameOnCard();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("student", student.getNic());
        map.put("paymentDescription", paymentDescription);
        map.put("paymentMethod", paymentMethodToSaveInDB);
        map.put("amountReceived", amountReceived + "");
        map.put("balancePayment", balancePayment + "");
        map.put("dueDateOfBalancePayment", dueDateOfBalancePayment + "");
        map.put("paymentDate", paymentDate + "");
        map.put("notes", notes);
        map.put("receiptDate", receiptDate + "");
        map.put("user", user.getUsername());
        map.put("balancePaymentReceipt", balancePaymentReceipt == null ? "null" : balancePaymentReceipt.getReceiptNumber() + "");
        return map;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptNumber=" + receiptNumber +
                ", student=" + student +
                ", paymentDescription='" + paymentDescription + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", amountReceived=" + amountReceived +
                ", balancePayment=" + balancePayment +
                ", dueDateOfBalancePayment=" + dueDateOfBalancePayment +
                ", paymentDate=" + paymentDate +
                ", notes='" + notes + '\'' +
                ", receiptDate=" + receiptDate +
                ", user=" + user +
                ", balancePaymentReceipt=" + balancePaymentReceipt +
                '}';
    }
}
