package com.revature.cmdBanking.daos;

import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.util.ConnectionFactory;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import sun.awt.image.ImageWatched;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

// Contains the functions necessary for the Account class to work in the database
public class AccountDAO implements CrudDAO<Account>{

    // Finds an account from a given username and account name
    public Account findAccountByUserAndName(AppUser user, String accName){
        // Empty Account to insert into
        Account result = new Account();

        // List for account user functionality
        List<AppUser> users = new LinkedList<AppUser>();
        users.add(user);

        // Connection
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            // First select accounts the user is tied to
            String sql = "select * from account_users where user_id = ? order by account_id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getId());
            ResultSet rs = pstmt.executeQuery();

            ResultSet rs2 = null;
            // Find the accounts from the previous query's results
            if (rs.next()){
                String sql2 = "select * from accounts where account_id = ? and account_name = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, rs.getString("account_id"));
                pstmt2.setString(2, accName);
                rs2 = pstmt2.executeQuery();
            }


            // Turn the results into an account.
            if (rs2.next()){
                Account target = new Account();
                target.setID(rs2.getString("account_id"));
                target.setName(rs2.getString("account_name"));
                target.setBalance(rs2.getDouble("balance"));
                target.setUsers(users);
                return target;
            }


        }catch (SQLException e) {
            e.printStackTrace();
    }
        return null;
    }

    // Finds an account from a given username
    public List<Account> findAccountsByUser(String username){

        // Find the user from the username
        AppUserDAO userDAO = new AppUserDAO();
        AppUser queryUser = userDAO.findUserByUsername(username);

        // Empty array for multiple users
        List<AppUser> users = new LinkedList<AppUser>();

        // Array for accounts
        List<Account> qAccounts = new LinkedList<Account>();

        // Attempts a connection
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            // Sorted selected values by account_id for later use
            String sql = "select * from account_users where user_id = ? order by account_id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,queryUser.getId());
            ResultSet rs = pstmt.executeQuery();

            // Find the accounts from the previous query's results
            ResultSet rs2 = null;
            while(rs.next()) {
                // Grab the account itself from the relation.
                sql = "select * from accounts where account_id = ? order by account_id";
                PreparedStatement pstmt2 = conn.prepareStatement(sql);
                pstmt2.setString(1,rs.getString("account_id"));
                rs2 = pstmt2.executeQuery();

                // Grab the account info from this new command
                Account listItem = new Account();
                if (rs2.next()) {
                    listItem.setID(rs2.getString("account_id"));
                    listItem.setBalance(rs2.getDouble("balance"));
                    listItem.setName(rs2.getString("account_name"));
                }

                users.add(userDAO.findUserByUsername(username));
                listItem.setUsers(users);
                users = null;

                qAccounts.add(listItem);
            }

            return qAccounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if something goes wrong.
        return null;
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
            pstmt.setDouble(2,newAccount.getBalance());
            pstmt.setString(3, newAccount.getName());

            // Test to make sure the rows inserted
            int rowsInserted = pstmt.executeUpdate();

            // Use a counter to loop insert into account_users for each user in an account.
            int userCount = 0;
            int appRowsInserted = 0;
            while (userCount < newAccount.getUsers().size())
            {
                System.out.println("DEBUG: inserting into account_users!");
                String sql2 = "insert into account_users (account_id, user_id) values (?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1,newAccount.getId());
                pstmt2.setString(2,newAccount.getUsers().get(userCount).getId());
                appRowsInserted = pstmt2.executeUpdate();
                userCount = userCount + 1;
            }




            if (rowsInserted != 0 && appRowsInserted != 0) {
                return newAccount;
            }

        } catch (SQLException e) {
            // TODO: Catch this and log a custom exception
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
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            // Update the Account table
            String sql = "update accounts set balance = ?, account_name = ? where account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, updatedObj.getBalance());
            pstmt.setString(2, updatedObj.getName());
            pstmt.setString(3, updatedObj.getId());
            int row1Updates = pstmt.executeUpdate();

            // Wipe previous relations
            String sql2 = "delete from account_users where account_id = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, updatedObj.getId());
            int row2Updates = pstmt2.executeUpdate();

            // Add new relations
            int userIndex = 0;
            while (userIndex < updatedObj.getUsers().size()){
                String sql3 = "insert into account_users (account_id, user_id) values (?, ?)";
                PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                pstmt3.setString(1, updatedObj.getId());
                pstmt3.setString(2, updatedObj.getUsers().get(userIndex).getId());
                int row3Updates = pstmt3.executeUpdate();
                userIndex = userIndex + 1;
            }
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
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
