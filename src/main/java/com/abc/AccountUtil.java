package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.abc.Account.AccountType;

public class AccountUtil {
	private static Random rn = new Random();
	public static int createAccountNum(AccountType acctType) {
		switch(acctType) {
		case CHECKING:
			/*add specific logic here to create the account number,
			such as get the account number from the sequence cache*/
			return rn.nextInt(100000);
		case SAVINGS: 
			//for demonstration purpose
			return rn.nextInt(100000);
		case MAXI_SAVINGS:
			//for demonstration purpose
			return rn.nextInt(100000);
		default:
			throw new RuntimeException("Unsupported Account Type.");
		}
	}
	
	public static Date getEarlierDate(int days) {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days * (-1));
        return cal.getTime();
	}
}
