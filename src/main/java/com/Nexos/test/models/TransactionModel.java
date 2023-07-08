package com.Nexos.test.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private boolean cacelled;

    private LocalDateTime transactionDate;

    private double amount;
    

    public TransactionModel() {
    }

    public TransactionModel(Long transactionId, CardModel card, boolean cacelled, LocalDateTime transactionDate, double amount) {
        this.transactionId = transactionId;
        this.card = card;
        this.cacelled = cacelled;
        this.transactionDate = transactionDate;
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

    public boolean isCacelled() {
        return this.cacelled;
    }

    public boolean getCacelled() {
        return this.cacelled;
    }

    public void setCacelled(boolean cacelled) {
        this.cacelled = cacelled;
    }

    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
