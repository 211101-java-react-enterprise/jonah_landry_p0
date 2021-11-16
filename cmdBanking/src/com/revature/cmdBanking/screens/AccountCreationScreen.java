package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class AccountCreationScreen extends Screen{
    private final AccountService accountService;
    private final UserService userService;

    public AccountCreationScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService){
        super("AccountCreationScreen", "/create-account", consoleReader, router);
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();
        boolean invalidName = true;
        String aName = null;
        // Make sure they put in a name that is unique per their account.
        while (invalidName) {
            System.out.print("Welcome to account creation!\n" +
                    "Account Name > ");
            aName = consoleReader.readLine();
            int counter = 0;
            invalidName = false;
            while (counter < accountService.getAccounts(sessionUser).size())
            {
                if (aName == accountService.getAccounts(sessionUser).get(counter).getName()){
                    System.out.println("You've already used that name!");
                    invalidName = true;
                }
            }
        }

        // Allows the user to input multiple users to tie to an account.
        String aUser = "temp";
        List<String> newUsers = new LinkedList<String>();
        // Starts by adding the current user
        newUsers.add(sessionUser.getUsername());
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

        // Create the new account
        Account newAccount = new Account(aName);
        try {
            accountService.createAccount(newAccount, newUsers);
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            System.out.println(e.getMessage());
        }

    }
}
