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
                "5) Transfer between accounts\n" + // Transfer
                "6) Transactions\n" + // Transactions
                "7) Add new user\n" + // Users
                "8) Go back\n" +
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
                router.navigate("/deposit");
                break;
            case "5":
                router.navigate("/transfer");
                break;
            case "6":
                router.navigate("/transactions");
                break;
            case "7":
                router.navigate("/add-user");
                break;
            case "8":
                break;
            default:
                System.out.println("You have made an invalid selection");
        }


    }

}


