package com.syzton.sunread.controller.tag;

import com.syzton.sunread.exception.tag.BookTagNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Chenty
 */
@ControllerAdvice
public class BooktagErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooktagErrorHandler.class);

    @ExceptionHandler(BookTagNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleBookTagNotFoundException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }
}
