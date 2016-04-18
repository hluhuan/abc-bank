package com.abc;

import java.util.Date;

public class Interest {
	private final double val;
	private final Date interestDate;
	
	public Interest(double val) {
		this.val = val;
		interestDate = DateProvider.getInstance().now();
	}

	public double getVal() {
		return val;
	}

	public Date getInterestDate() {
		return interestDate;
	}
	
}
