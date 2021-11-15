package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.AccountDAO;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;

public class AccountService {

    private final AccountDAO accountDAO;
    private Account currAccount;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.currAccount = null;
    }

    // Add a new user to the account
    public boolean addUser(AppUser newUser){
        // Checks if the data is valid.
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }
    }

    // Checks each individual value within app User to make sure they're valid.
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }
}
