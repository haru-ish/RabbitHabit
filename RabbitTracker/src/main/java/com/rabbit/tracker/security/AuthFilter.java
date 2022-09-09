package com.rabbit.tracker.security;

import java.lang.Object;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class AuthFilter extends AbstractPreAuthenticatedProcessingFilter { 
	
	private static final String TOKEN_PREFIX = "Bearer ";
	
	@Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		// No Principal is used for authentication this time
		return "";
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        final Object token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!token.toString().startsWith(TOKEN_PREFIX)) {
            // if the token does not start with 'Bearer '
            return "";
        }
        // Return the characters after 'Bearer '
        return Optional.ofNullable(token.toString().substring(TOKEN_PREFIX.length())).orElse("");
    }
	

}
