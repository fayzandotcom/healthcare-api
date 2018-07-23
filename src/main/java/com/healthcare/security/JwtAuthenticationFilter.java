package com.healthcare.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.healthcare.dao.UserRepository;
import com.healthcare.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getParameter("email"),
                        req.getParameter("password"),
                        new ArrayList<>())
        );
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userEmail = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(userEmail)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        
        User user = userRepository.findByEmail(userEmail);
        if (user!=null) {
            user.setLastLoginDate(new Date());
            userRepository.save(user);
        }
        
        String jsonObject = "{\"token\": \""+token+"\"}";
        res.setContentType("application/json");      
        PrintWriter out = res.getWriter();  
        out.print(jsonObject);
        out.flush();
    }


}
