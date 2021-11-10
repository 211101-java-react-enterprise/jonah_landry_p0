package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.AppUserDAO;
import com.revature.cmdBanking.exceptions.AuthenticationException;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.models.AppUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UserService {

    private AppUserDAO userDAO = new AppUserDAO();

    public boolean registerNewUser(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        // TODO: create a new error code


        File usersFile = new File("resources/data.txt");
        boolean unique = true;

        try(BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            for(String line; (line = br.readLine()) != null; ) {

                String[] each = line.split(":");
                if( each[4].equals(newUser.getUsername())){
                    System.out.println("User already exists with that Username!");
                    unique = false;
                }
                if ( each[3].equals(newUser.getEmail()) ) {
                    System.out.println("User already exists with that Email!");
                    unique = false;
                }

            }
        } catch(Exception e) {
            throw new ResourcePersistenceException("File Reading Error!");
        }

        AppUser registeredUser = null;
        if (unique) {
             registeredUser = userDAO.save(newUser);
        }

        if (registeredUser == null) {
            return false;
        }

        return true;

    }

    public AppUser authenticateUser(String username, String password) {
        File usersFile = new File("resources/data.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            for(String line; (line = br.readLine()) != null; ) {

                String[] each = line.split(":");
                if( each[4].equals(username) && each[5].equals(password)){
                    AppUser authUser = new AppUser(each[1],each[2], each[3], each[4], each[5]);
                    authUser.setId(each[0]);
                    return authUser;
                }
            }
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

}