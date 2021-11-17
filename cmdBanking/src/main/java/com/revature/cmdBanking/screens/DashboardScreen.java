package com.revature.cmdBanking.screens;

import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen{
    private final UserService userService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();
        if (sessionUser == null) {
            System.out.println("You are not current logged in! Returning to Login Screen.");
            router.navigate("/login");
            return;
        }

        while (userService.isSessionActive()) {
            System.out.printf("\n%s's Dashboard\n", sessionUser.getFirstName());

            String menu = "1) View/edit my profile WIP\n" +
                    "2) Manage my accounts\n" +
                    "3) Logout\n";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("User management selected.");
                    router.navigate("/user_screen");
                    break; // TODO: Route to a screen where they can edit/delete the user
                case "2":
                    System.out.println("Account management selected.");
                    router.navigate("/accounts");
                    break;
                case "3":
                    userService.logout();
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
        }





    }

}
