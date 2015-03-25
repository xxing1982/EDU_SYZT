package com.syzton.sunread.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.syzton.sunread.model.common.AbstractEntity;
@Entity
@Table(name = "oauth_authentication_access_token")
public class OAuth2AuthenticationAccessToken extends AbstractEntity {

    @Column(name="token_id")
	private String tokenId;
    
    @Transient
    private OAuth2AccessToken oAuth2AccessToken;
    
    @Column(name="authentication_id")
    private String authenticationId;
    
    @Column(name="user_name")
    private String userName;
    
    @Column(name="client_id")
    private String clientId;
    
    @Transient
    private OAuth2Authentication authentication;
    
    @Column(name="refresh_token")
    private String refreshToken;

    public OAuth2AuthenticationAccessToken() {
    }

    public OAuth2AuthenticationAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
        this.tokenId = oAuth2AccessToken.getValue();
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = authentication;
        this.refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
    }

    public String getTokenId() {
        return tokenId;
    }

    public OAuth2AccessToken getoAuth2AccessToken() {
        return oAuth2AccessToken;
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

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
