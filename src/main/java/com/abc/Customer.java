package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.Account.AccountType;
import com.abc.Transaction.TransType;

public class Customer {
    private String name;
//    private List<Account> accounts;
    private Map<AccountType, List<Account>> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<AccountType, List<Account>>();
    }
    
    public Map<AccountType, List<Account>> getAccountsMap() {
		return accounts;
	}

	public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
    	AccountType acctType = account.getAccountType();
    	if(accounts.containsKey(acctType)) {
    		accounts.get(acctType).add(account);
    	} else {
    		List<Account> accountList = new ArrayList<Account>();
    		accountList.add(account);
    		accounts.put(acctType, accountList);
    	}
        return this;
    }

    public int getNumberOfAccounts(AccountType acctType) {
    	if(accounts.get(acctType) == null) {
    		return 0;
    	} else {
    		return accounts.get(acctType).size();
    	}
    }
    
    public int getNumberOfAccounts() {
    	int num = 0;
    	for(AccountType acctType : accounts.keySet()) {
    		num += accounts.get(acctType).size();
    	}
    	return num;
    }

    public double totalInterestEarned() {
        double total = 0;
        for(AccountType acctType : accounts.keySet()) {
        	List<Account> acctList = accounts.get(acctType);
        	if(acctList != null) {
        		for (Account a : acctList) {
        			total += a.interestEarned();
        		}
        	}
        }
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        double total = 0.0;
        statement.append("Statement for " + name);
        for(AccountType acctType : accounts.keySet()) {
        	List<Account> acctList = accounts.get(acctType);
        	if(acctList != null) {
        		for (Account a : acctList) {
        			statement.append("\n" + statementForAccount(a) + "\n");
                    total += a.getBalance();
        		}
        	}
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }
    
    //this method should be exposed as public, because the customer should be able to see the statement for every single account
    public String statementForAccount(Account a) {
        StringBuilder sb = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                sb.append("Checking Account\n");
                break;
            case SAVINGS:
                sb.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                sb.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
        	sb.append("  ");
        	sb.append(t.getTransType() == TransType.DEBIT ? "withdrawal" : "deposit");
        	sb.append(" ").append(toDollars(t.getAmount())).append("\n");
            total += t.getAmount();
        }
        sb.append("Total ").append(toDollars(total));
        return sb.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public boolean transfer(Account from, Account to, double amount) {
    	if(amount < 0) {
    		throw new IllegalArgumentException("amount should be greater than zero");
    	}
    	
    	if(amount > from.getBalance()) {
    		throw new IllegalArgumentException("amount should not be greater than balance in the from account");
    	}
    	
    	from.withdraw(amount);
    	to.deposit(amount);
    	return true;
    }
}
