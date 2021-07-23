package service;

import model.Receipt;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;
import service.exception.NotFoundException;
import util.FileIO;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService {
    public static ArrayList<Receipt> receiptList = new ArrayList();
    private static final File receiptDBFile = new File("sps-receipts.db");
    private static FileIO fileIO = new FileIO();

    static {
        ArrayList arrayList = fileIO.readDataFromFile(receiptList, receiptDBFile);
        if (arrayList != null) receiptList = arrayList;
    }

    public void addReceipt(Receipt receipt) throws DuplicateEntryException, FailedOperationException {
        if (getReceipt(receipt.getReceiptNumber()) != null) {
            throw new DuplicateEntryException();
        }
        receiptList.add(receipt);

        if (!fileIO.writeDataToFile(receiptList, receiptDBFile)) {
            receiptList.remove(receipt);
            throw new FailedOperationException();
        }
    }

    public void updateReceipt(Receipt receiptToUpdate) throws NotFoundException, FailedOperationException {
        Receipt receiptBeforeUpdate = searchReceipt(receiptToUpdate.getReceiptNumber());
        receiptList.set(receiptList.indexOf(receiptBeforeUpdate), receiptToUpdate);

        if (!fileIO.writeDataToFile(receiptList, receiptDBFile)) {
            receiptList.set(receiptList.indexOf(receiptToUpdate), receiptBeforeUpdate);
            throw new FailedOperationException();
        }
    }

    public long getLastReceiptNumber() {
        long lastReceiptNumber = receiptList.size();
        for (int i = 0; i < receiptList.size(); i++) {
            if (getReceipt(lastReceiptNumber) == null) {
                return lastReceiptNumber == 0 ? 0 : lastReceiptNumber - 1;
            }
            lastReceiptNumber++;
        }
        return lastReceiptNumber - 1;
    }

    public List<Receipt> searchAllReceipts() {
        return receiptList;
    }

    public Receipt searchReceipt(long receiptNumber) throws NotFoundException {
        Receipt receipt = getReceipt(receiptNumber);

        if (receipt != null) {
            return receipt;
        }
        throw new NotFoundException();
    }

    public List<Receipt> searchReceiptsByKeyword(String keyword) {
        if (keyword.equals("")) {
            return searchAllReceipts();
        }
        keyword = keyword.toLowerCase();
        List<Receipt> searchResult = new ArrayList();

        for (Receipt receipt : receiptList) {
            if (Long.toString(receipt.getReceiptNumber()).contains(keyword) ||
                    receipt.getPaymentDescription().toLowerCase().contains(keyword) ||
                    receipt.getPaymentDate().toString().contains(keyword) ||
                    receipt.getReceiptDate().toString().contains(keyword) ||
                    receipt.getAmountReceived().toString().contains(keyword) ||
                    receipt.getPaymentMethod().toString().contains(keyword) ||
                    receipt.getStudent().getNic().toLowerCase().contains(keyword) ||
                    receipt.getStudent().getNameInFull().toLowerCase().contains(keyword) ||
                    receipt.getUser().getNic().contains(keyword) ||
                    receipt.getUser().getNameInFull().toLowerCase().contains(keyword)) {
                searchResult.add(receipt);
            }
        }
        return searchResult;
    }

    public List<Receipt> searchReceiptsByKeyword(String keyword, LocalDate startingDate, LocalDate endDate) {
        if (keyword.equals("") && (startingDate == null || endDate == null)) {
            return searchAllReceipts();
        }

        if (startingDate == null || endDate == null) {
            return searchReceiptsByKeyword(keyword);
        }

        keyword = keyword.toLowerCase();
        List<Receipt> searchResult = new ArrayList();

        for (Receipt receipt : receiptList) {
            if (!(receipt.getReceiptDate().isBefore(endDate) && (receipt.getReceiptDate().isEqual(startingDate) || receipt.getReceiptDate().isAfter(startingDate)))) {
                continue;
            }

            if (Long.toString(receipt.getReceiptNumber()).contains(keyword) ||
                    receipt.getPaymentDescription().toLowerCase().contains(keyword) ||
                    receipt.getPaymentDate().toString().contains(keyword) ||
                    receipt.getReceiptDate().toString().contains(keyword) ||
                    receipt.getAmountReceived().toString().contains(keyword) ||
                    receipt.getPaymentMethod().toString().contains(keyword) ||
                    receipt.getStudent().getNic().toLowerCase().contains(keyword) ||
                    receipt.getStudent().getNameInFull().toLowerCase().contains(keyword) ||
                    receipt.getUser().getNic().contains(keyword) ||
                    receipt.getUser().getNameInFull().toLowerCase().contains(keyword)) {
                searchResult.add(receipt);
            }
        }
        return searchResult;
    }

    private Receipt getReceipt(long receiptNumber) {
        for (Receipt receipt : receiptList) {
            if (receipt.getReceiptNumber() == receiptNumber) {
                return receipt;
            }
        }
        return null;
    }
}
