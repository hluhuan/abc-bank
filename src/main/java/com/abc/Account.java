package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction.TransType;

public class Account {
	public enum AccountType {
		CHECKING, SAVINGS, MAXI_SAVINGS;
	}
	
	private final int accountNum;
	private final AccountType accountType;
	private List<Transaction> transactions;
	private double balance;
	private List<Interest> interests;
	
	public Account(AccountType accountType) {
		this.accountNum = AccountUtil.createAccountNum(accountType);
		this.accountType = accountType;
		this.balance = 0.0;
		this.transactions = new ArrayList<Transaction>();
		this.interests = new ArrayList<Interest>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			balance += amount;
			transactions.add(new Transaction(amount, TransType.CREDIT));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0 || amount > balance) {
			throw new IllegalArgumentException(
					"amount must be greater than zero and must be less than the balance");
		} else {
			balance -= amount;
			transactions.add(new Transaction(-amount, TransType.DEBIT));
		}
	}

	public double interestEarned() {
		double total = 0.0;
		for(Interest interest : interests) {
			total += interest.getVal();
		}
		
		return total;
	}


	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public double getBalance() {
		return balance;
	}

	public int getAccountNum() {
		return accountNum;
	}

	private Transaction getLastWithdrawnTransaction() {
		Transaction ret = null;
		if(transactions.isEmpty()) {
			return null;
		} else {
			for(int i=transactions.size()-1; i>=0; i --) {
				if(transactions.get(i).getTransType() == TransType.DEBIT) {
					ret = transactions.get(i);
					break;
				}
			}
		}
		
		return ret;
	}
	
	/*
	 * This method should be called by autosys job daily to update the user interest and balance since interest is compounding daily.
	 */
	public void caculateInterestForToday() {
		double interestVal = 0.0;
		switch (accountType) {
		case SAVINGS:
			if (balance <= 1000)
				interestVal = balance * 0.001/365;
			else
				interestVal = 1.0/365 + (balance - 1000) * 0.002/365;
			break;
		case MAXI_SAVINGS:
			Transaction tran = getLastWithdrawnTransaction();
			if(tran == null || tran.getTransactionDate().before(AccountUtil.getEarlierDate(10))) {
				interestVal =  balance * 0.05/365;
			} else {
				interestVal = balance * 0.001/365;
			}
			break;	
		case CHECKING:
			interestVal = balance * 0.001/365;
			break;
		default: 
			throw new RuntimeException("Unsupported Account Type: " + accountType);
		}
		balance += interestVal;
		Interest interest = new Interest(interestVal);
		interests.add(interest);
	}
}
