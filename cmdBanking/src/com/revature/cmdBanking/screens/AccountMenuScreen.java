package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class AccountMenuScreen extends Screen{
    public AccountMenuScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("AccountMenuScreen", "/accounts", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.print("Account menu\n" +
                "1) View my accounts\n" +
                "2) Create new account\n" +
                "3) Make a withdraw\n" +
                "4) Make a deposit\n" +
                "5) Transfer WIP\n" + // Transfer
                "6) Transactions WIP\n" + // Transactions
                "7) Go back\n" +
                "> ");
        String userSelection = consoleReader.readLine();
        switch (userSelection) {
            case "1":
                router.navigate("/view-accounts");
                break;
            case "2":
                router.navigate("/create-account");
                break;
            case "3":
                router.navigate("/withdraw");
                break;
            case "4":
                System.out.println("Work in progress!");
                //router.navigate("/deposit");
                break;
            case "5":
                System.out.println("Work in progress!");
                //router.navigate("/transfer");
                break;
            case "6":
                System.out.println("Work in progress!");
                //router.navigate("/transactions");
                break;
            case "7":
                break;
            default:
                System.out.println("You have made an invalid selection");
        }

        System.out.println("Navigating back to your dashboard...");

    }

}


