package com.syzton.sunread.exception.common;

public class DatabaseException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 4657491283614455649L;

	public DatabaseException(String item) {
		super(item + " encountered a database exception");
	}

}
