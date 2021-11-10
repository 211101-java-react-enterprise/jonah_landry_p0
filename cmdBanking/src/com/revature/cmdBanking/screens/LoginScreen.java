package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.ScreenRouter;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.BufferedReader;


public class LoginScreen extends Screen {
    private final UserService userService;

    // Declares the screen by taking the consolreader used allthroughout, a route for easier access, and a userservice
    // to work around
    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    // Render is the meat of the screen. What the users will see when it loads up.
    @Override
    public void render() throws Exception {
        System.out.println("The user selected Login");
        System.out.print("Username: ");
        String givenUser = consoleReader.readLine();

        System.out.print("Password: ");
        String givenPassword = consoleReader.readLine();

        System.out.println("Fetching user...");


        AppUser authUser = userService.authenticateUser(givenUser,givenPassword);
        // TODO: Keep track of authUser within the program.
        if (authUser != null) {
            System.out.println("Login successful!");
            // router.navigate("/dashboard");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }


    }
}

