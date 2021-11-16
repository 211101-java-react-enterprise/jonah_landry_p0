package com.revature.cmdBanking.util;

import com.revature.cmdBanking.daos.AccountDAO;
import com.revature.cmdBanking.screens.*;
import com.revature.cmdBanking.services.AccountService;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.daos.AppUserDAO;
import com.revature.cmdBanking.models.AppUser;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

/*
    Encapsulate of application state. We will create a few things in here that will be used throughout the
    application:
        - a common BufferedReader that all screens can use to read from the console
        - a ScreenRouter that can be used to navigate from one screen to another
        - a boolean that indicates if the app is still running or not
 */
public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true; // Boolean value that we can change when we want to end the application.
        router = new ScreenRouter(); // Uses our screenrouter util which allows us to add/move through screens in the linked list.
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); //Input reader

        //
        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        //
        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService(accountDAO, userService);

        // Adds our screens. If we make more screens, add them here!
        router.addScreen(new WelcomeScreen(consoleReader, router)); // Welcome
        router.addScreen(new RegisterScreen(consoleReader, router, userService)); // Registration
        router.addScreen(new LoginScreen(consoleReader, router, userService)); // Login
        router.addScreen(new DashboardScreen(consoleReader, router, userService));// Dashboard - Screen that follows login or registration. Allows users to access/create accounts
        router.addScreen(new userScreen(consoleReader, router, userService));// ManageAccount - Shows all accounts associated with the user and allows them to move to the appropriate
        router.addScreen(new AccountMenuScreen(consoleReader, router)); // Menu for accounts
        router.addScreen(new AccViewScreen(consoleReader, router, userService, accountService)); // View accounts associated with a user
        router.addScreen(new AccountCreationScreen(consoleReader, router, userService, accountService));// CreateAccount - Screen for account creation
        //               Can be used to see transaction history, add users to joint accounts, withdraw, deposit, or close
        //               an account.


    }

    public void startup() {

        try {
            while (appRunning) {
                router.navigate("/welcome"); //Starts everything by heading to the welcome menu.
            }
        } catch (Exception e) {
            e.printStackTrace(); // Prints out errors if we have an issue going to welcome.
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}
