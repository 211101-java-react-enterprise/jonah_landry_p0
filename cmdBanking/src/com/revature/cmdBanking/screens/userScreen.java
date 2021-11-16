package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class userScreen extends Screen{
    private final UserService userService;
    public userScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("UserScreen", "/user_screen", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception{
        System.out.print( "User Menu\n" +
                "1) Delete user WIP" +
                "2) Update User" +
                "3) Return");
        String userSelection = consoleReader.readLine();
        switch (userSelection) {
            case "1":
                System.out.println("This will eventually delete the account and all mentions of it.");
                break;
            case "2":
                System.out.println("This will allow user info to be changed.");
                break;
            case "3":
                break;
            default:
                System.out.println("You have made an invalid selection");
        }
        System.out.println("Navigating back to your dashboard...");
    }

}
