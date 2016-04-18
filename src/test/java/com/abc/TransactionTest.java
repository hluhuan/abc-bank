package com.abc;

import org.junit.Test;

import com.abc.Transaction.TransType;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, TransType.DEBIT);
        assertTrue(t instanceof Transaction);
    }
}
