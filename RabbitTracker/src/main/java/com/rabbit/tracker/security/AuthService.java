package com.rabbit.tracker.security;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.rabbit.tracker.repository.UserRepository;
import com.rabbit.tracker.util.FirebaseClient;

public class AuthService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private FirebaseClient firebaseClient;
	private UserRepository userRepository;

	public AuthService(FirebaseClient firebaseClient) {
		this.firebaseClient = firebaseClient;
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

		// Get the token from AuthFilter
		final String credential = token.getCredentials().toString();

		if (credential.isEmpty()) {
			throw new BadCredentialsException("Token is Empty");
		}
		try {
			// Check the Token
			FirebaseToken firebaseToken = firebaseClient.verify(credential);
			System.out.println("pui pui molcar");
			// Check if the token exists in the database?
			
			//return new User("", "", AuthorityUtils.NO_AUTHORITIES);

            return userRepository.findByEmail(firebaseToken.getEmail())
                    .map(user -> new User("h.ono@frevo-works.co.jp", "", AuthorityUtils.NO_AUTHORITIES))
                    .orElseThrow(() -> new UsernameNotFoundException("該当するユーザが存在しません"));

		} catch (FirebaseException e) {
			throw new BadCredentialsException("Token check error", e);
		}
	}

}
