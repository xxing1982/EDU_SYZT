package com.syzton.sunread.exception.common;

 
public class TodayVerifyTimesOverException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8790211652911971729L;

	public TodayVerifyTimesOverException(String msg) {
		super(msg);
	}

	public TodayVerifyTimesOverException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}
}