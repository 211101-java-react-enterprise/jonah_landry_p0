package com.revature.cmdBanking.daos;

import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.models.Transaction;
import com.revature.cmdBanking.util.ConnectionFactory;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;

import java.sql.*;
import java.sql.Time;
import java.util.UUID;

// Contains the functions necessary for the Transaction class

public class TransactionDAO implements CrudDAO<Transaction>{
    // Finds a transaction from a given account.
    public List<Transaction> findTransactionsByAccount(Account qAccount){
        // Empty list for results
        List<Transaction> transactions = new LinkedList<Transaction>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from transactions where account_id = ? order by ttime";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qAccount.getId());
            ResultSet rs = pstmt.executeQuery();
            // Get each transaction done on this account and save them.
            while (rs.next()){
                Transaction temp = new Transaction(rs.getDouble("amount"));
                temp.setAccount_id(rs.getString("account_id"));
                temp.setTransaction_id(rs.getString("transaction_id"));
                temp.setUser_id(rs.getString("user_id"));
                temp.settTime(rs.getTimestamp("ttime"));
                transactions.add(temp);
            }

            return transactions;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Transaction save(Transaction newTransaction){
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            // Sets the transaction's UUID
            newTransaction.setTransaction_id(UUID.randomUUID().toString());
            long now = System.currentTimeMillis();
            newTransaction.settTime(new Timestamp(now));

            // Pushes the account into the database
            String sql = "insert into transactions (transaction_id, account_id, user_id, amount, ttime) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newTransaction.getTransaction_id());
            pstmt.setString(2, newTransaction.getAccount_id());
            pstmt.setString(3, newTransaction.getUser_id());
            pstmt.setDouble(4, newTransaction.getAmount());
            pstmt.setTimestamp(5, newTransaction.gettTime());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0){
                return newTransaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Transaction updatedObj) {
        return false;
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    @Override
    public Transaction findById(String id) {
        return null;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}


