package com.revature.cmdBanking.daos;

import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.util.ConnectionFactory;
import com.revature.cmdBanking.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

// Contains the functions necessary for the Account class
public class AccountDAO implements CrudDAO<Account>{

    // Finds an account from a given username
    public List<Account> findAccountsByUser(String username){

        // Find the user from the username
        AppUserDAO userDAO = new AppUserDAO();
        AppUser queryUser = userDAO.findUserByUsername(username);

        // Empty array for multiple users
        List<String> users;


        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from account_users where user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,queryUser.getId());
            ResultSet rs = pstmt.executeQuery();

            // TODO: Figure out logic for multiple queries.
            if (rs.next()) {
                Account listItem = new Account();
                listItem.setID(rs.getString("account_id"));
                listItem.setBalance(rs.getDouble("balance"));
                listItem.setName(rs.getString("account_name"));
                //users.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Saves a new account into the database
    @Override
    public Account save(Account newAccount) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            // Sets the account's UUID
            newAccount.setID(UUID.randomUUID().toString());

            // Pushes the account into the database
            String sql = "insert into accounts (account_id, balance, account_name) values (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,newAccount.getId());
            pstmt.setString(2,String.valueOf(newAccount.getBalance()));
            pstmt.setString(3, newAccount.getName());

            // Use a counter to loop insert into account_users for each user in an account.
            int userCount = 0;
            while (userCount < newAccount.getUsers().length)
            {
                String sql2 = "insert into account_users (account_id, user_id) values (?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1,newAccount.getId());
                pstmt2.setString(2,newAccount.getUsers()[userCount]);
                userCount = userCount + 1;
            }


            // Test to make sure the rows inserted
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newAccount;
            }

        } catch (SQLException e) {
            // TODO: Catch this and log a custom excepttion
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public boolean update(Account updatedObj) {
        return false;
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}
