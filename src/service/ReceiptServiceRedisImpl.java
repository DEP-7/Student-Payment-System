/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package service;

import model.Receipt;
import model.Student;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.JedisClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReceiptServiceRedisImpl {

    private static final String DB_PREFIX = "r#";
    private final Jedis client;

    public ReceiptServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void addReceipt(Receipt receipt) throws DuplicateEntryException {

        if (client.exists(DB_PREFIX + receipt.getReceiptNumber())) {
            throw new DuplicateEntryException();
        }

        client.hset(DB_PREFIX + receipt.getReceiptNumber(), receipt.toMap());
    }

    public void updateReceipt(Receipt receiptToUpdate) throws NotFoundException {

        if (!client.exists(DB_PREFIX + receiptToUpdate.getReceiptNumber())) {
            throw new NotFoundException();
        }

        client.hset(DB_PREFIX + receiptToUpdate.getReceiptNumber(), receiptToUpdate.toMap());
    }

    public List<Receipt> searchAllReceipts() {
        List<Receipt> receiptList = new ArrayList<>();
        Set<String> receiptNumberList = client.keys(DB_PREFIX + "*");

        for (String receiptNumber : receiptNumberList) {
            receiptList.add(Receipt.fromMap(receiptNumber.substring(2), client.hgetAll(receiptNumber)));
        }

        return receiptList;
    }

    public Receipt searchReceipt(long receiptNumber) throws NotFoundException {

        if (!client.exists(DB_PREFIX + receiptNumber)) {
            throw new NotFoundException();
        }
        return Receipt.fromMap(receiptNumber + "", client.hgetAll(DB_PREFIX + receiptNumber));
    }

    public List<Receipt> searchReceiptsByKeyword(String keyword) {
        return searchReceiptsByKeyword(keyword, null, null);
    }

    public List<Receipt> searchReceiptsByKeyword(String keyword, LocalDate startingDate, LocalDate endDate) {

        if (keyword.isEmpty() && startingDate == null && endDate == null) {
            return searchAllReceipts();
        }

        keyword = keyword.toLowerCase();
        List<Receipt> searchResult = new ArrayList();
        Set<String> receiptNumberList = client.keys(DB_PREFIX + "*");

        for (String receiptNumber : receiptNumberList) {

            if ((startingDate != null && startingDate.isBefore(LocalDate.parse(client.hget(receiptNumber, "receiptDate")))) || (endDate != null && endDate.isAfter(LocalDate.parse(client.hget(receiptNumber, "receiptDate"))))) {
                continue;
            }

            if (receiptNumber.substring(2).contains(keyword)) {
                searchResult.add(Receipt.fromMap(receiptNumber.substring(2), client.hgetAll(receiptNumber)));
            } else {
                for (String data : client.hvals(receiptNumber)) {
                    if (data.toLowerCase().contains(keyword)) {
                        searchResult.add(Receipt.fromMap(receiptNumber.substring(2), client.hgetAll(receiptNumber)));
                        break;
                    }
                }
            }
        }
        return searchResult;
    }

    public List<Receipt> searchReceiptsByStudent(Student student) {
        List<Receipt> searchResult = new ArrayList();
        Set<String> receiptNumberList = client.keys(DB_PREFIX + "*");

        for (String receiptNumber : receiptNumberList) {
            if (client.hget(receiptNumber,"student").equals(student.getNic())) {
                searchResult.add(Receipt.fromMap(receiptNumber.substring(2), client.hgetAll(receiptNumber)));
            }
        }
        return searchResult;
    }

    public long getLastReceiptNumber() {
        Set<String> receiptNumberList = client.keys(DB_PREFIX + "*");
        int count = receiptNumberList.size() + 1;

        for (int i = 0; i < receiptNumberList.size() + 1; i++) {
            if (!client.exists(DB_PREFIX + count)) {
                return count == 0 ? 0 : count - 1;
            }
            count++;
        }
        return count - 1;
    }
}
