/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package service;

import model.Receipt;
import model.sub.CashPayment;
import model.sub.OnlinePayment;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService {
    public static ArrayList<Receipt> receiptDB = new ArrayList();

    static {
        receiptDB.add(new Receipt(1, StudentService.studentDB.get(0), "Registration Fee", new CashPayment(), new BigDecimal("50000"), new BigDecimal("0"), null, LocalDate.of(2021, 5, 2), "", LocalDate.of(2021, 5, 2), UserService.usersDB.get(0), null));
        receiptDB.add(new Receipt(2, StudentService.studentDB.get(1), "Installment", new OnlinePayment("212536542452"), new BigDecimal("20000"), new BigDecimal("30000"), LocalDate.of(2021, 6, 30), LocalDate.of(2021, 6, 20), "", LocalDate.now(), UserService.usersDB.get(0), null));
        receiptDB.add(new Receipt(3, StudentService.studentDB.get(0), "Installment", new OnlinePayment("212536542452"), new BigDecimal("10000"), new BigDecimal("30000"), LocalDate.of(2021, 6, 30), LocalDate.of(2021, 6, 20), "", LocalDate.now(), UserService.usersDB.get(0), null));
        receiptDB.add(new Receipt(4, StudentService.studentDB.get(1), "Installment", new OnlinePayment("212536542452"), new BigDecimal("5000"), new BigDecimal("30000"), LocalDate.of(2021, 6, 30), LocalDate.of(2021, 6, 20), "", LocalDate.now(), UserService.usersDB.get(0), null));
    }

    public void addReceipt(Receipt receipt) throws DuplicateEntryException {
        if (getReceipt(receipt.getReceiptNumber()) != null) {
            throw new DuplicateEntryException();
        }
        receiptDB.add(receipt);
    }

    public void updateReceipt(Receipt receiptToUpdate) throws NotFoundException {
        Receipt receiptBeforeUpdate = searchReceipt(receiptToUpdate.getReceiptNumber());
        receiptDB.set(receiptDB.indexOf(receiptBeforeUpdate), receiptToUpdate);
    }

    public long getLastReceiptNumber() {
        long lastReceiptNumber = receiptDB.size();
        for (int i = 0; i < receiptDB.size(); i++) {
            if (getReceipt(lastReceiptNumber) == null) {
                return lastReceiptNumber == 0 ? 0 : lastReceiptNumber - 1;
            }
            lastReceiptNumber++;
        }
        return lastReceiptNumber - 1;
    }

    public List<Receipt> searchAllReceipts() {
        return receiptDB;
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

        for (Receipt receipt : receiptDB) {
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

        for (Receipt receipt : receiptDB) {
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
        for (Receipt receipt : receiptDB) {
            if (receipt.getReceiptNumber() == receiptNumber) {
                return receipt;
            }
        }
        return null;
    }
}
