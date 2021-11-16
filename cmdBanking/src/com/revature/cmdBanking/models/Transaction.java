package com.revature.cmdBanking.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

// Contains the data of the Transaction class
public class Transaction extends Object{
    private String transaction_id;
    private String account_id;
    private String user_id;
    private double amount;
    private Timestamp tTime;

     public Transaction(double amount){
         this.amount = amount;
     }

     // Getters
    public String getTransaction_id() {
        return transaction_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp gettTime() {
        return tTime;
    }

    // Setters
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void settTime(Timestamp tTime) {
        this.tTime = tTime;
    }
}
