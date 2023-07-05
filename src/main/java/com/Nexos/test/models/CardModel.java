package com.Nexos.test.models;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class CardModel {
    
    @Id
    private long cardId;
   
    private String name;
    private String lastName;
    private LocalDate expirationDate;
    private boolean activate;
    private double balance;
    private boolean blockedCard;
    


    public CardModel() {
    }

    public CardModel(long cardId,String name, String lastName, LocalDate expirationDate, boolean activate, double balance, boolean blockedCard) {
        this.cardId = cardId;
      
        this.name = name;
        this.lastName = lastName;
        this.expirationDate = expirationDate;
        this.activate = activate;
        this.balance = balance;
        this.blockedCard = blockedCard;
        
    }

    public long getCardId() {
        return this.cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActivate() {
        return this.activate;
    }

    public boolean getActivate() {
        return this.activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBlockedCard() {
        return this.blockedCard;
    }

    public boolean getBlockedCard() {
        return this.blockedCard;
    }

    public void setBlockedCard(boolean blockedCard) {
        this.blockedCard = blockedCard;
    }
       

}
