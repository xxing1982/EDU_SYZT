package com.syzton.sunread.exception.exam;

public class HaveVerifiedBookException  extends RuntimeException  {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = -2481602479447638928L;

	public HaveVerifiedBookException(String msg) {
		super(msg);
	}

	public HaveVerifiedBookException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}
}
