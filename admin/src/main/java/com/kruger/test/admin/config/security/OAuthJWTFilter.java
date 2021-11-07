package com.kruger.test.admin.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kruger.test.admin.rest.service.UserDetailImplService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class OAuthJWTFilter extends OncePerRequestFilter {

    @Autowired
    OAuthJWTProvider jwtProvider;

    @Autowired
    UserDetailImplService userDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(req);
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails users = userDetail.loadUserByUsername(username);

                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(users, null,
                        users.getAuthorities());
        
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                SecurityContextHolder.clearContext();
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            e.printStackTrace();
        }
        filterChain.doFilter(req, res);
    }

    public String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }

}
