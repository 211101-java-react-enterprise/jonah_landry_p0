package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class transferScreen extends Screen{
    private final UserService userService;
    private final AccountService accountService;

    public transferScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("TransferScreen", "/transfer", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception{
        AppUser sessionUser = userService.getSessionUser();
        List<Account> accounts = new LinkedList<Account>();
        accounts = accountService.getAccounts(sessionUser);

        System.out.print("Transfer menu\n" );
        int accountCount = 0;
        while (accountCount < accounts.size()){
            System.out.println( accounts.get(accountCount).getName() +
                    " : " + accounts.get(accountCount).getBalance() +
                    "$");
            accountCount++;
        }
        System.out.print("Input source account name: ");
        String userInput = consoleReader.readLine();

        Account source = accountService.getAccount(sessionUser, userInput);
        if (source == null) throw new InvalidRequestException("An account with that name belonging to this user does not exist.");


        System.out.println("Input destination account name: ");
        userInput = consoleReader.readLine();

        Account dest = accountService.getAccount(sessionUser, userInput);
        if (source == null) throw new InvalidRequestException("An account with that name belonging to this user does not exist.");

        System.out.print(source.getName() +
                ":\nBalance = " +
                source.getBalance() +
                "\n ---> " +
                dest.getName() +
                "\n\tBalance = " +
                dest.getBalance() +
                "\nHow much do you want to send?\n" +
                "> ");
        userInput = consoleReader.readLine();

        // Take out of the first account
        double amtTaken = Math.abs(Double.parseDouble(userInput));
        amtTaken = amtTaken * -1.0;
        source = accountService.exchange(source, amtTaken);

        // Add into the second
        amtTaken = Math.abs(amtTaken);
        dest = accountService.exchange(dest, amtTaken);

        System.out.println("Transaction successful! \n" + source.getName()+
                "'s Balance: " +
                source.getBalance() + "\n" + dest.getName() + "'s Balance: "+
                dest.getBalance());

        System.out.println("Type anything to exit.");
        userInput = consoleReader.readLine();

        router.navigate("/accounts");
    }
}
