package com.syzton.sunread.exception.common;

public class DatabaseException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 4657491283614455649L;

	public DatabaseException(String msg) {
		super(msg);
	}

	public DatabaseException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}

}
