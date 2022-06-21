package com;

import java.io.Serializable;

public class Job implements Executable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8625510160309083541L;

	public String execute() {
		int n = 5, prod = 1;
		for (int i = 2; i <= n; i++)
			prod *= i;
		return (new Integer(prod)).toString();
	}
}
