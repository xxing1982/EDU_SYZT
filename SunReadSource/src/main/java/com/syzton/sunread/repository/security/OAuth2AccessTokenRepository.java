package com.syzton.sunread.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.security.OAuth2AuthenticationAccessToken;

@Repository
public interface OAuth2AccessTokenRepository extends JpaRepository<OAuth2AuthenticationAccessToken, Long> {
	
	public OAuth2AuthenticationAccessToken findByTokenId(String tokenId);

    public OAuth2AuthenticationAccessToken findByRefreshToken(String refreshToken);

    public List<OAuth2AuthenticationAccessToken> findByAuthenticationIdOrderByCreationTimeDesc(String authenticationId);

    public List<OAuth2AuthenticationAccessToken> findByClientIdAndUserName(String clientId, String userName);

    public List<OAuth2AuthenticationAccessToken> findByClientId(String clientId);
}
