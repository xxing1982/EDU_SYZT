package com.syzton.sunread.exception.common;

public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = -7367200004299809010L;
	

	public AuthenticationException(String msg) {
		super(msg);
	}

	public AuthenticationException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
 
	}
}
	
