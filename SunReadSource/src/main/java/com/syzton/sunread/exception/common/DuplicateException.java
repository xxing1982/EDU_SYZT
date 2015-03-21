package com.syzton.sunread.exception.common;

import org.springframework.dao.DataIntegrityViolationException;

/*
 * When add a duplicate item the exception should be thrown
 */

public class DuplicateException extends DataIntegrityViolationException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 8810853019521983157L;

	public DuplicateException(String msg) {
		super(msg);
	}

	public DuplicateException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}
}

