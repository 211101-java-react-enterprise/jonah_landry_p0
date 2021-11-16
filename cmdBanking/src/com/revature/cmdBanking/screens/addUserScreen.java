package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.exceptions.AuthenticationException;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class addUserScreen extends Screen{
    private final UserService userService;
    private final AccountService accountService;

    public addUserScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService){
        super("AddUserScreen", "/add-user", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    public void render() throws Exception{
        AppUser sessionUser = userService.getSessionUser();
        List<Account> accounts = new LinkedList<Account>();
        accounts = accountService.getAccounts(sessionUser);
        Account selected = new Account();

        System.out.print("Add users menu\n");
        int accountCount = 0;
        while (accountCount < accounts.size()){
            System.out.println( accounts.get(accountCount).getName() +
                    " : " + accounts.get(accountCount).getBalance() +
                    "$");
            accountCount++;
        }
        System.out.print("Input Account Name: ");
        String userInput = consoleReader.readLine();
        selected = accountService.getAccount(sessionUser,userInput);

        // Allows the user to input multiple users to tie to an account.
        String aUser = "temp";
        List<AppUser> newUsers = new LinkedList<AppUser>();
        while (!aUser.equals(""))
        {
            System.out.print("Input any additional users or\n" +
                    "input nothing to indicate you are done adding users\n" +
                    ">");
            aUser = consoleReader.readLine();
            if (!aUser.equals("")){
                AppUser user = userService.getByUsername(aUser);
                newUsers.add(user);
            }
        }
        int userCount = 0;
        while (userCount < newUsers.size()) {
            if (accountService.addUser(newUsers.get(userCount), selected)){

            } else {
                System.out.println(newUsers.get(userCount).getUsername() + " already exists on this account!");
            }
            userCount++;
        }
        System.out.println("Users added!");



    }
}
