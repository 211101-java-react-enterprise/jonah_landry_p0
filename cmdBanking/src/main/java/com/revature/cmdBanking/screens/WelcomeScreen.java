package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

import static com.revature.cmdBanking.util.AppState.shutdown;

public class WelcomeScreen extends Screen {
    private final UserService userService;

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        // Route back into the menu if there's an active user
        if (userService.getSessionUser() != null) router.navigate("/dashboard");
        // Shows the user their three initial options.
        System.out.print("Welcome to CMD Banking!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = consoleReader.readLine();

        // Handles the user's three valid inputs as well as looping them back in if their input is invalid.
        switch (userSelection) {
            // To a login screen
            case "1":
                router.navigate("/login");
                break;
            // To a registration screen
            case "2":
                router.navigate("/register");
                break;
            // Fully exits the program when they select exit.
            case "3":
                System.out.println("Exiting application...");
                shutdown();
                break;
            case "throw exception":
                throw new RuntimeException(); // "throw" is used to explicitly throw an exception that will (hopefully) be handled elsewhere
            default:
                System.out.println("The user made an invalid selection");
        }

    }
}
