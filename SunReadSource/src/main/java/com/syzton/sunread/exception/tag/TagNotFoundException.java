package com.syzton.sunread.exception.tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.syzton.sunread.exception.common.SomethingNotFoundException;

/**
 * @author Petri Kainulainen
 */
public class TagNotFoundException extends Exception {
	
    public TagNotFoundException(String message) {
        super(message);
    }
}
