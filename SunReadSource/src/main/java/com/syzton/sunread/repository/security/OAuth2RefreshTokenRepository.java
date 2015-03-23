package com.syzton.sunread.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.security.OAuth2AuthenticationRefreshToken;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 23/05/2013
 */
public interface OAuth2RefreshTokenRepository extends JpaRepository<OAuth2AuthenticationRefreshToken, String> {

    public OAuth2AuthenticationRefreshToken findByTokenId(String tokenId);
}
