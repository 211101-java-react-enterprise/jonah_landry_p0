package com.revature.cmdBanking.services;


import com.revature.cmdBanking.daos.AccountDAO;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.AppUser;
import com.revature.cmdBanking.util.LinkedList;
import com.revature.cmdBanking.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountServiceTestSuite {
    AccountDAO mockAccountDAO;
    UserService mockUS;
    AccountService sut;

    @Before
    public void testCaseSetup() {
        mockAccountDAO = mock(AccountDAO.class);
        mockUS = mock(UserService.class);
        sut = new AccountService(mockAccountDAO, mockUS);
    }

    @After
    public void testCaseCleanup() { sut = null;}

    @Test(expected = InvalidRequestException.class)
    public void test_addUser_throwsError_givenNoID(){
        // Arrange
        AppUser badUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        Account validAccount = new Account("valid");
        try{
            boolean actualResult = sut.addUser(badUser, validAccount);
        }finally {
            verify(mockAccountDAO, times(0)).update(validAccount);
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_getAccounts_throwsError_givenNoID(){
        // Arrange
        AppUser badUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        try{
            List<Account> actualResult = sut.getAccounts(badUser);
        }finally {
            verify(mockAccountDAO, times(0)).findAccountsByUser(badUser.getUsername());
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_getAccount_throwsError_givenNoID(){
        // Arrange
        AppUser badUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        String testString = "valid";
        try{
            Account actualResult = sut.getAccount(badUser, testString);
        }finally {
            verify(mockAccountDAO, times(0)).findAccountByUserAndName(badUser,testString);
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_exchange_throwsError_givenOverdraft(){
        Account validAccount = new Account("valid");
        validAccount.setID(UUID.randomUUID().toString());
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        validUser.setId(UUID.randomUUID().toString());
        List<AppUser> validUsers = new LinkedList<AppUser>();
        validUsers.add(validUser);
        validAccount.setUsers(validUsers);
        double badChange = -900;

        try{
            Account actualResult = sut.exchange(validAccount, badChange);
        }finally {
            verify(mockAccountDAO, times(0)).save(validAccount);
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_createAccount_throwsError_givenBadAccount(){
        Account badAccount = new Account("invalid");
        badAccount.setBalance(-900);
        List<String> badUsers = new LinkedList<String>();

        try {
            sut.createAccount(badAccount, badUsers);
        }finally {
            verify(mockAccountDAO, times(0)).save(badAccount);
        }
    }

    @Test
    public void test_isAccountValid_returnsTrue_givenValidAccount() {
        // Users for the account
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        validUser.setId(UUID.randomUUID().toString());
        List<AppUser> validUsers = new LinkedList<AppUser>();
        validUsers.add(validUser);

        // Account itself
        Account validAccount = new Account("valid");
        validAccount.setID(UUID.randomUUID().toString());

        boolean actualResult = sut.isAccountValid(validAccount);

        Assert.assertTrue("Expecting account to be consdidred valid", actualResult);

    }


}
