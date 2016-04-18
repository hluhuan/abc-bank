package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        assertEquals("Statement for Henry\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts(Account.AccountType.SAVINGS));
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts(Account.AccountType.SAVINGS) + oscar.getNumberOfAccounts(Account.AccountType.CHECKING) );
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testAccountTransfer() {
    	 Account checkingAccount = new Account(Account.AccountType.CHECKING);
         Account savingsAccount = new Account(Account.AccountType.SAVINGS);

         Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

         checkingAccount.deposit(200.0);
         savingsAccount.deposit(200.0);
         
         henry.transfer(checkingAccount, savingsAccount, 100);
         assertEquals(100, checkingAccount.getBalance(), DOUBLE_DELTA);
         assertEquals(300, savingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void testAccountInterest() {
    	 Account maxSavingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
    	 Account checkingAccount = new Account(Account.AccountType.CHECKING);
         Account savingsAccount = new Account(Account.AccountType.SAVINGS);
         
    	 Customer henry = new Customer("Henry").openAccount(maxSavingAccount).openAccount(checkingAccount).openAccount(savingsAccount);
    	 checkingAccount.deposit(100.0);
         savingsAccount.deposit(100.0);
         maxSavingAccount.deposit(100.0);
         
         checkingAccount.caculateInterestForToday();
         savingsAccount.caculateInterestForToday();
         maxSavingAccount.caculateInterestForToday();
         
         assertTrue(Math.abs((checkingAccount.getBalance() - 100.00027397260274)) < 0.000001);
         assertTrue(Math.abs((savingsAccount.getBalance() - 100.00027397260274)) < 0.0000001);
         assertTrue(Math.abs((maxSavingAccount.getBalance() - 100.01369863013699)) < 0.000001);
         
         assertEquals(100.00027397260274, checkingAccount.getBalance(), DOUBLE_DELTA);
         assertEquals(100.00027397260274, savingsAccount.getBalance(), DOUBLE_DELTA);
         assertEquals(100.01369863013699, maxSavingAccount.getBalance(), DOUBLE_DELTA);
    }
}
