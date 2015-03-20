package com.syzton.sunread.exception.tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Petri Kainulainen
 */
public class BookTagNotFoundException extends Exception {

    public BookTagNotFoundException(String message) {
        super(message);
    }

}
