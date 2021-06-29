package model.sub;

public class OnlinePayment implements PaymentMethod {
    private String referenceNumber;
    private String fileName;

    public OnlinePayment() {
    }

    public OnlinePayment(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public OnlinePayment(String referenceNumber, String fileName) {
        this.referenceNumber = referenceNumber;
        this.fileName = fileName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Online Payment";
    }
}
