package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.AppUserDAO;
import com.revature.cmdBanking.exceptions.AuthenticationException;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.models.AppUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// Contains functions that the user itself can perform
public class UserService {

    private final AppUserDAO userDAO ;
    private AppUser sessionUser;

    public UserService(AppUserDAO userDAO) {
        this.userDAO = userDAO;
        this.sessionUser = null;
    }

    // Keeps track of the currently logged-in user which is tracked here.
    public AppUser getSessionUser() {
        return sessionUser;
    }

    // Registers a new user
    public boolean registerNewUser(AppUser newUser) {
        // Checks if the data is valid.
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        // Checks if the given username/emails are unique and refuses them if they aren't.
        boolean usernameAvailable = userDAO.findUserByUsername(newUser.getUsername()) == null;
        boolean emailAvailable = userDAO.findUserByEmail(newUser.getEmail()) == null;


        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users:";
            if (!usernameAvailable) msg = msg + "\n\t- username";
            if (!emailAvailable) msg = msg + "\n\t- email";
            throw new ResourcePersistenceException(msg);
        }

        // Saves the user into the system
        AppUser registeredUser = userDAO.save(newUser);

        // Returns if the user is invalid somehow
        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return true;

    }

    // Checks that a user has the correct username and password.
    public void authenticateUser(String username, String password) {

        //Makes sure that the given values are valid before checking them
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        AppUser authenticatedUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        sessionUser = authenticatedUser;

    }

    // Resets to no logged user.
    public void logout() {
        sessionUser = null;
    }

    // Returns if the session is still active.
    public boolean isSessionActive() {
        return sessionUser != null;
    }

    // Checks each individual value within app User to make sure they're valid.
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

}
