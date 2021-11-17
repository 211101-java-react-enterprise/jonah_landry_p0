package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.TransactionDAO;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.Transaction;
import com.revature.cmdBanking.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO;
    private final UserService userService;
    private final AccountService accountService;

    public TransactionService(TransactionDAO transactionDAO, UserService userService, AccountService accountService){
        this.transactionDAO = transactionDAO;
        this.userService = userService;
        this.accountService = accountService;
    }

    public List<Transaction> getTransactionsFromAccount(Account target){
        if (accountService.isAccountValid(target)) return transactionDAO.findTransactionsByAccount(target);
        else throw new InvalidRequestException("Invalid Account provided!");
    }

}
