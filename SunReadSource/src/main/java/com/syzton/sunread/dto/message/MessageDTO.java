package com.syzton.sunread.dto.message;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jerry on 4/7/15.
 */
public class MessageDTO {

    @NotEmpty
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
