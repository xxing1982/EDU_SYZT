package com.syzton.sunread.exception.common;

public class UpLoadException extends RuntimeException {


	public UpLoadException(String msg) {
		super(msg);
	}

	public UpLoadException(String msg, Exception e) {
		super(msg + " because of " + e.toString());
	}

}
