package com.syzton.sunread.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.security.OAuth2AuthenticationRefreshToken;

@Repository
public interface OAuth2RefreshTokenRepository extends JpaRepository<OAuth2AuthenticationRefreshToken, String> {

    public OAuth2AuthenticationRefreshToken findByTokenId(String tokenId);
}
