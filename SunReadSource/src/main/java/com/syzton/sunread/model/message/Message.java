package com.syzton.sunread.model.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name="message")
public class Message extends AbstractEntity{
	
	public static final int MAX_LENGTH_MESSAGE = 1000;
	
	@Column(name = "message",nullable = false,length = MAX_LENGTH_MESSAGE)
	private String message;
	
	@Column(name = "is_read",nullable = false)
	private Boolean isRead = false;

	private long sendUserId;

	private long receiveUserId;

	public long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void read() {
		this.isRead = true;
	}

}
