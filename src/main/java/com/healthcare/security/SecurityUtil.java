package com.healthcare.security;

import io.jsonwebtoken.Jwts;

public class SecurityUtil {
    
    public static final String getUserEmailFromToken(String token) {
        String userEmail = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        return userEmail;
    }

}
