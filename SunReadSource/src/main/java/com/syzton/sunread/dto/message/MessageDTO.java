package com.syzton.sunread.dto.message;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by jerry on 4/7/15.
 */
public class MessageDTO {

    @NotEmpty
    private String message;

    private Boolean isRead = false;

    private long sendUserId;

    private long receiveUserId;

    private String sendUsername;

    private String receiveUserName;

    private Date creationTime;


    public Boolean getIsRead() {
        return isRead;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
