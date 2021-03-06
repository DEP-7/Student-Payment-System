/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package model.sub;

import java.time.LocalDate;

public class CardPayment implements PaymentMethod{
    private String cardNumber;
    private LocalDate cardExpireDate;
    private String nameOnCard;

    public CardPayment() {
    }

    public CardPayment(String cardNumber, LocalDate cardExpireDate, String nameOnCard) {
        this.cardNumber = cardNumber;
        this.cardExpireDate = cardExpireDate;
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getCardExpireDate() {
        return cardExpireDate;
    }

    public void setCardExpireDate(LocalDate cardExpireDate) {
        this.cardExpireDate = cardExpireDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    @Override
    public String toString() {
        return "Card Payment";
    }
}
