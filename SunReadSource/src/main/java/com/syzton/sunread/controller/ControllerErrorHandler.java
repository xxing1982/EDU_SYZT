package com.syzton.sunread.controller;

import com.syzton.sunread.exception.common.DatabaseException;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.common.dto.ValidationErrorDTO;

import com.syzton.sunread.exception.store.InSufficientCoinsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * @author Chenty
 */
@ControllerAdvice
public class ControllerErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerErrorHandler.class);

    private MessageSource messageSource;

    @Autowired
    public ControllerErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }
    
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDuplicateException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }
    
    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleDatabaseException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleSQLException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleNumberFormatException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(InSufficientCoinsException.class)
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    @ResponseBody
    public String handleInSufficientCoinsException(Exception ex) {
        LOGGER.debug(ex.getMessage());
        return ex.getMessage();
    }
}
