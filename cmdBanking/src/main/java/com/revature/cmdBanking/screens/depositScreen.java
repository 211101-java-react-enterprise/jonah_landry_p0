package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.screens.Screen;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class depositScreen extends Screen {
    private final UserService userService;
    private final AccountService accountService;

    public depositScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("DepositScreen", "/deposit", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();
        List<Account> accounts = new LinkedList<Account>();
        accounts = accountService.getAccounts(sessionUser);

        System.out.print("Deposit menu\n");
        int accountCount = 0;
        while (accountCount < accounts.size()){
            System.out.println( accounts.get(accountCount).getName() +
                    " : " + accounts.get(accountCount).getBalance() +
                    "$");
            accountCount++;
        }
        System.out.print("Input Account Name: ");
        String userInput = consoleReader.readLine();

        Account target = accountService.getAccount(sessionUser, userInput);
        if (target == null)
            throw new InvalidRequestException("An account with that name belonging to this user does not exist.");

        System.out.print(target.getName() +
                ":\nBalance = " +
                target.getBalance() +
                "\nHow much do you want to deposit?\n" +
                "> ");
        userInput = consoleReader.readLine();
        double amtTaken = Math.abs(Double.parseDouble(userInput));

        target = accountService.exchange(target, amtTaken);
        System.out.println("Transaction successful! Your new balance is: " +
                target.getBalance() + "$");

        System.out.println("Type anything to exit.");
        userInput = consoleReader.readLine();

        router.navigate("/accounts");
    }
}
