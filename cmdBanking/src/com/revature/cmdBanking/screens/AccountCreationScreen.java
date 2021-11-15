package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class AccountCreationScreen extends Screen{
    private final AccountService accountService;

    public AccountCreationScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService){
        super("AccountCreationScreen", "/create-account", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        System.out.print("Welcome to account creation!\n" +
                            "Account Name > ");
        String aName = consoleReader.readLine();


        // Allows the user to input multiple users to tie to an account.
        String aUser = "temp";
        List<String> newUsers = new LinkedList<String>();
        while (!aUser.equals(""))
        {
            System.out.print("Input any additional users or\n" +
                    "input nothing to indicate you are done adding users\n" +
                    ">");
            aUser = consoleReader.readLine();
            if (!aUser.equals("")){
                newUsers.add(aUser);
            }
        }


        Account newAccount = new Account(aName);
        try {
            accountService.createAccount(newAccount, newUsers);
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Navigating back to Account menu...");
    }
}
