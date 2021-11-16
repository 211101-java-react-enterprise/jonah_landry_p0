package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.models.Transaction;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.TransactionService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class transactionScreen extends Screen{

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    public transactionScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService, TransactionService transactionService) {
        super("TransactionScreen","/transactions", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();
        List<Account> accounts = new LinkedList<Account>();
        List<Transaction> transactions = new LinkedList<Transaction>();
        accounts = accountService.getAccounts(sessionUser);

        System.out.print("Transaction menu\n");
        int accountCount = 0;
        while (accountCount < accounts.size()){
            System.out.println( accounts.get(accountCount).getName() +
                    " : " + accounts.get(accountCount).getBalance() +
                    "$");
            accountCount++;
        }
        System.out.print("Input Account Name: ");
        String userInput = consoleReader.readLine();

        // Fetch our transactions
        transactions = transactionService.getTransactionsFromAccount(accountService.getAccount(sessionUser,userInput));
        int transCount = 0;
        while (transCount < transactions.size()) {
            System.out.println("Date: " + transactions.get(transCount).gettTime() +
                    "\tAmount: " + transactions.get(transCount).getAmount() + "\n");
            transCount ++;
        }
        System.out.println("Type anything to exit.");
        userInput = consoleReader.readLine();
        router.navigate("/accounts");

    }
}
