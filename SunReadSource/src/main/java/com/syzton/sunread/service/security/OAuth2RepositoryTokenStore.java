package com.syzton.sunread.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.syzton.sunread.model.security.OAuth2AuthenticationAccessToken;
import com.syzton.sunread.model.security.OAuth2AuthenticationRefreshToken;
import com.syzton.sunread.repository.security.OAuth2AccessTokenRepository;
import com.syzton.sunread.repository.security.OAuth2RefreshTokenRepository;

public class OAuth2RepositoryTokenStore implements TokenStore{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OAuth2RepositoryTokenStore.class);
	
	private final OAuth2AccessTokenRepository oAuth2AccessTokenRepository;

    private final OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Autowired
    public OAuth2RepositoryTokenStore(final OAuth2AccessTokenRepository oAuth2AccessTokenRepository,
                                      final OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository) {
        this.oAuth2AccessTokenRepository = oAuth2AccessTokenRepository;
        this.oAuth2RefreshTokenRepository = oAuth2RefreshTokenRepository;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenId) {
    	OAuth2AuthenticationAccessToken  token = oAuth2AccessTokenRepository.findByTokenId(tokenId);
        return SerializationUtils.deserialize(token.getAuthentication());
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = new OAuth2AuthenticationAccessToken(token,
                authentication, authenticationKeyGenerator.extractKey(authentication));
        oAuth2AccessTokenRepository.save(oAuth2AuthenticationAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AuthenticationAccessToken token = oAuth2AccessTokenRepository.findByTokenId(tokenValue);
        if(token == null) {
            return null; //let spring security handle the invalid token
        }
        OAuth2AccessToken accessToken = SerializationUtils.deserialize(token.getToken());
		return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        OAuth2AuthenticationAccessToken accessToken = oAuth2AccessTokenRepository.findByTokenId(token.getValue());
        if(accessToken != null) {
            oAuth2AccessTokenRepository.delete(accessToken);
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
         oAuth2RefreshTokenRepository.save(new OAuth2AuthenticationRefreshToken(refreshToken, authentication));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return oAuth2RefreshTokenRepository.findByTokenId(tokenValue).getoAuth2RefreshToken();
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return oAuth2RefreshTokenRepository.findByTokenId(token.getValue()).getAuthentication();
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        oAuth2RefreshTokenRepository.delete(oAuth2RefreshTokenRepository.findByTokenId(token.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        oAuth2AccessTokenRepository.delete(oAuth2AccessTokenRepository.findByRefreshToken(refreshToken.getValue()));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        List<OAuth2AuthenticationAccessToken> tokenList =  oAuth2AccessTokenRepository.findByAuthenticationIdOrderByCreationTimeDesc(authenticationKeyGenerator.extractKey(authentication));
        OAuth2AccessToken token = null;
        if(tokenList.size() != 0) {SerializationUtils.deserialize(tokenList.get(0).getToken());}
        return token;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AuthenticationAccessToken> tokens = oAuth2AccessTokenRepository.findByClientId(clientId);
        return extractAccessTokens(tokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AuthenticationAccessToken> tokens = oAuth2AccessTokenRepository.findByClientIdAndUserName(clientId, userName);
        return extractAccessTokens(tokens);
    }

    private Collection<OAuth2AccessToken> extractAccessTokens(List<OAuth2AuthenticationAccessToken> tokens) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        for(OAuth2AuthenticationAccessToken token : tokens) {
            accessTokens.add((OAuth2AccessToken)SerializationUtils.deserialize(token.getToken()));
        }
        return accessTokens;
    }
}
