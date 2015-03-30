package com.syzton.sunread.model.messagecenter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.model.user.User;

@Entity
@Table(name="message")
@JsonIgnoreProperties(value = {"reviews"})
public class Message {
	
	public static final int MAX_LENGTH_MESSAGE = 1000;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "send_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime sendTime;
	
	@Column(name = "receive_time",nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime receiveTime;
	
	@Column(name = "ontent",nullable = false,length = MAX_LENGTH_MESSAGE)
	private String messageContent;
	
	@Column(name = "is_read",nullable = false)
	private Boolean isRead;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
	@JoinColumn(name = "send_user")
	private User sendUser;

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
	@JoinColumn(name = "receive_user")
	private User receiveUser;

	public Long getId() {
		return id;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(DateTime sendTime) {
		this.sendTime = sendTime;
	}

	public DateTime getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(DateTime receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	
	
}
