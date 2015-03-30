package com.syzton.sunread.model.security;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;
@Entity
@Table(name = "oauth_authentication_access_token")
public class OAuth2AuthenticationAccessToken extends AbstractEntity implements Serializable  {


	private static final long serialVersionUID = 4671313927076038150L;


	@Column(name="token_id")
	private String tokenId;
    
    
    @Column(name="authentication_id")
    private String authenticationId;
    
    @Column(name="user_name")
    private String userName;
    
    @Column(name="client_id")
    private String clientId;
    
    @Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="token", columnDefinition="BLOB")
    private byte[] token;
    
    @Lob 
    @Basic(fetch = FetchType.LAZY) 
    @Column(name="authentication", columnDefinition="BLOB")
    private byte[] authentication; 
    
    @Column(name="refresh_token")
    private String refreshToken;

    public OAuth2AuthenticationAccessToken() {
    }
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.userName == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        OAuth2AuthenticationAccessToken that = (OAuth2AuthenticationAccessToken) obj;

        return this.userName.equals(that.getUserName());
    }
    public OAuth2AuthenticationAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
        this.tokenId = oAuth2AccessToken.getValue();
        this.token = SerializationUtils.serialize(oAuth2AccessToken);
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = SerializationUtils.serialize(authentication);
        this.refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
    }

    public String getTokenId() {
        return tokenId;
    }

    

    public String getAuthenticationId() {
        return authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public String getClientId() {
        return clientId;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}
	
	public byte[] getToken(){
		return this.token;
	}
	
	public byte[] getAuthentication(){
		return this.authentication;
	}
	
	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
