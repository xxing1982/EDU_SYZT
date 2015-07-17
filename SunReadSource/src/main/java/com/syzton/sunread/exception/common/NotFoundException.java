package com.syzton.sunread.exception.common;


/**
 * An application-specific exception. When something not found,
 * throw this exception, both the console and the front-end will
 * show the hints.
 */

public class NotFoundException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8790211652911971729L;

	public NotFoundException(String msg) {
		super(msg);
	}

	public NotFoundException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}
}
