package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class AccViewScreen extends Screen{
    private final UserService userService;
    private final AccountService accountService;

    public AccViewScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("ViewAccounts", "/view-accounts", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();

        System.out.printf("\n%s's Accounts\n", sessionUser.getFirstName());

        List<Account> sessionAccounts = new LinkedList<Account>();
        sessionAccounts = accountService.getAccounts(sessionUser);

        int counter = 0;
        while (counter < sessionAccounts.size()) {
            System.out.println(sessionAccounts.get(counter).getName() +
                    ":\nBalance = " +
                    sessionAccounts.get(counter).getBalance());
            counter ++;
        }

        if (counter == 0) System.out.println("No accounts associated with current user!");

    }
}
