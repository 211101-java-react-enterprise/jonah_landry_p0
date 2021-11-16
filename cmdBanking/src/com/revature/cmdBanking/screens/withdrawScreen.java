package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class withdrawScreen extends Screen{
    private final UserService userService;
    private final AccountService accountService;

    public withdrawScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("withdrawScreen", "/withdraw", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception{
        System.out.print("Withdraw menu\n" +
                "Input account name");
    }
}
