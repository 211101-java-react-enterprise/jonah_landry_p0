package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.AccountDAO;
import com.revature.cmdBanking.models.Account;

public class AccountService {

    private final AccountDAO accountDAO;
    private Account currAccount;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.currAccount = null;
    }
}
