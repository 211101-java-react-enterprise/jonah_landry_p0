package com.revature.cmdBanking.daos;

import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.util.List;

import java.io.*;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

    // TODO: Implement me!
    public AppUser findUserByUsernameAndPassword(String username, String password) {
        File usersFile = new File("resources/data.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            for(String line; (line = br.readLine()) != null; ) {

                String[] each = line.split(":");
                System.out.println("DEBUG: line: " + line);
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

    @Override
    public AppUser save(AppUser newUser) {

        File usersFile = new File("resources/data.txt");

        // try-with-resources (allows for the instantiation of AutoCloseable types that are implicitly
        // closed when the try logic is finished)
        try (FileWriter fileWriter = new FileWriter(usersFile, true)) {

            // Universally Unique IDentifier (UUID)
            String uuid = UUID.randomUUID().toString();
            newUser.setId(uuid);
            fileWriter.write(newUser.toFileString() + "\n");

        } catch (Exception e) {
            e.printStackTrace(); // leave for debugging purposes (preferably, write it to a file) - definitely remove before "production"
            throw new RuntimeException("Error persisting user to file.");
        }

        return newUser;
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser findById(String id) {
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}