package com.syzton.sunread.model.security;

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;

public class VerificationToken extends AbstractEntity {
	private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; // 24 hours

	private final String token;

	private Date expiryDate;

	private boolean verified;

	Long userId;

	public VerificationToken() {
		super();
		this.token = UUID.randomUUID().toString();
		this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
	}

	public VerificationToken(User user, int expirationTimeInMinutes) {
		this();
		this.userId = user.getId();

		this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public String getToken() {
		return token;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		DateTime now = new DateTime();
		return now.plusMinutes(expiryTimeInMinutes).toDate();
	}

	public boolean hasExpired() {
		DateTime tokenDate = new DateTime(getExpiryDate());
		return tokenDate.isBeforeNow();
	}
}
