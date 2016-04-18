package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private final TransType transType;

    private Date transactionDate;

    public Transaction(double amount, TransType transType) {
        this.amount = amount;
        this.transType = transType;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
	public Date getTransactionDate() {
		return transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public TransType getTransType() {
		return transType;
	}

	public enum TransType{
    	DEBIT, CREDIT;
    }
}
