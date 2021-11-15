package com.revature.cmdBanking.models;

import java.util.ArrayList;
import java.util.Objects;

// Contains the data of the Account class
public class Account extends Object{

    private double balance;
    private String id;
    private String name;
    private String[] users;

    // When created accounts should have nothing but the initial users and the name
    public Account(String[] users, String name){
        this.users = users;
        this.name = name;
        this.balance = 0;
    }

    // Constructors
    public double getBalance(){return this.balance;}

    public void setBalance(double balance){this.balance = balance;}

    public Account(){super();}

    public String getId() { return id;}

    public void setID(String id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) {this.name = name;}

    public String[] getUsers() { return users;}

    public void setUsers(String[] users) { this.users = users;}


}
