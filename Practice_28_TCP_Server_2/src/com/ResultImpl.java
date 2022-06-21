package com;

import java.io.Serializable;

public class ResultImpl implements Result, Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -8672468227543908818L;
	String output;
	double completionTime;

	public ResultImpl(String o, double c) {
		output = o;
		completionTime = c;
	}

	public String output() {
		return output;
	}

	public double completionTime() {
		return completionTime;
	}
}
