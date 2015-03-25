package com.syzton.sunread.exception.tag;

/**
 * @author Petri Kainulainen
 */
public class BookTagNotFoundException extends Exception {

	private static final long serialVersionUID = 6338457594399375649L;

	public BookTagNotFoundException(String message) {
        super(message);
    }

}
