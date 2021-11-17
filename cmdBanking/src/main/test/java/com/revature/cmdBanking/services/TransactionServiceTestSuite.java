package com.revature.cmdBanking.services;


import com.revature.cmdBanking.daos.TransactionDAO;
import com.revature.cmdBanking.models.Account;
import com.revature.cmdBanking.models.Transaction;
import com.revature.cmdBanking.services.UserService;
import com.revature.cmdBanking.exceptions.InvalidRequestException;
import com.revature.cmdBanking.exceptions.ResourcePersistenceException;
import com.revature.cmdBanking.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TransactionServiceTestSuite {
    UserService mockUS;
    TransactionDAO mockActionDAO;
    AccountService mockAS;
    TransactionService sut;

    @Before
    public void testCaseSetup() {
        mockActionDAO = mock(TransactionDAO.class);
        mockUS = mock(UserService.class);
        mockAS = mock(AccountService.class);
        sut = new TransactionService(mockActionDAO, mockUS, mockAS);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test(expected = InvalidRequestException.class)
    public void test_TransactionFromAccount_throwsError_givenInvalidAccount() {
        // Arrange
        Account badTarget1 = new Account("validName"); // Negative balance
        badTarget1.setBalance(-45.34);
        Account badTarget2 = new Account("validName"); // No users

        // Act
        try
        {
            List<Transaction> actualResult = sut.getTransactionsFromAccount(badTarget1);
            actualResult = sut.getTransactionsFromAccount(badTarget2);
        } finally {
            verify(mockActionDAO, times(0));
        }
        // Assert
    }
}
