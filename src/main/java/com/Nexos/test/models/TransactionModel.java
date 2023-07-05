package com.Nexos.test.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;  

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardModel card;

    private double amount;


    public TransactionModel() {
    }


    public TransactionModel(Long transactionId, CardModel card, double amount) {
        this.transactionId = transactionId;
        this.card = card;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public CardModel getCard() {
        return this.card;
    }

    public void setCard(CardModel card) {
        this.card = card;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    





    
    
}
