package com.syzton.sunread.exception.store;

public class InSufficientCoinsException extends RuntimeException {
    /**
     * Unique ID for Serialized object
     */
    private static final long serialVersionUID = -8790217652911979729L;

    public InSufficientCoinsException(String msg) {
        super(msg);
    }

    public InSufficientCoinsException(String msg, Exception e) {
        super(msg + " because of " + e.toString());
    }
}