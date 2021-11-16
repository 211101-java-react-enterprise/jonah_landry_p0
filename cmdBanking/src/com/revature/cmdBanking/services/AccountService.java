package com.revature.cmdBanking.services;

import com.revature.cmdBanking.daos.AccountDAO;
import com.revature.cmdBanking.exceptions.AuthorizationException;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;

public class AccountService {

    private final AccountDAO accountDAO;
    private final UserService userService;

    public AccountService(AccountDAO accountDAO, UserService userService) {
        this.accountDAO = accountDAO;
        this.userService = userService;
    }

    // Add a new user to the account. TODO: Implement me baby
    public boolean addUser(AppUser newUser, Account tAccount){
        // Checks if the data is valid.
        if (!userService.isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }
        if (!userService.isSessionActive()) {
            throw new AuthorizationException("No active user session to perform operation!");
        }

        tAccount.getUsers().add(newUser);



        return true;
    }

    public List<Account> getAccounts(AppUser queryUser){
        return accountDAO.findAccountsByUser(queryUser.getUsername());
    }

    public Account getAccount(AppUser owner, String target){
        Account targetAccount = accountDAO.findAccountByUserAndName(owner, target);
        return targetAccount;
    }

    // Updates an account with the change from a withdraw/deposit to an account.
    public Account exchange(Account target, double change){
        target.setBalance(target.getBalance() + change);
        if (target.getBalance() < 0) {
            throw new InvalidRequestException("Insuffecient funds!");
        }
        else accountDAO.update(target);
        return target;
    }

    public void createAccount(Account newAccount, List<String> users){
        if (isAccountValid(newAccount)) throw new InvalidRequestException("Invalid account values provided!");

        // Adds the initial user
        List<AppUser> newUsers = new LinkedList<AppUser>();
        newUsers.add(userService.getSessionUser());

        // Adds additional users given in creation
        int currUserCounter = 0;
        while (currUserCounter < users.size()) {
            boolean unique = true;
            if (userService.getByUsername(users.get(currUserCounter)) != null) {
                // Logic to prevent repeat user inputs
                int savedUserCounter = 0;
                while (savedUserCounter < newUsers.size()){
                    if(newUsers.get(savedUserCounter).getUsername().equals(users.get(currUserCounter))){
                        unique = false;
                    }
                    savedUserCounter ++;
                }
                if (unique) newUsers.add(userService.getByUsername(users.get(currUserCounter)));

            }
            else {
                throw new InvalidRequestException("Given user does not exist!");
            }
            currUserCounter ++;
        }

        newAccount.setUsers(newUsers);
        Account addedAccount = accountDAO.save(newAccount);

        if (addedAccount == null){
            throw new ResourcePersistenceException("The account could not be persisted to the database!");
        }
    }

    public boolean isAccountValid(Account account){
        if (account == null) return false; // Check entire account
        if (account.getName() == null || account.getName().trim().equals("")) return false; // Check name
        if (account.getBalance() < 0) return false; // Check Balance
        if (account.getUsers() == null || account.getUsers().get(0).getUsername().equals("")) return false; // Check users
        return true;
    }

}
