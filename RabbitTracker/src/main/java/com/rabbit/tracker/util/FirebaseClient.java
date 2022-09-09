package com.rabbit.tracker.util;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PreDestroy;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseClient {
	@Value("classpath:service-account-file.json")
	private Resource serviceAccountResource;

	@Bean
	public FirebaseApp createFireBaseApp() throws IOException {
		InputStream serviceAccount = serviceAccountResource.getInputStream();
	 
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		// Initialize Firebase Admin Java SDK
		return FirebaseApp.initializeApp(options);
	}

	// sysoutが出力されない→使われてない？
	public FirebaseToken verify(String token) throws FirebaseAuthException {
		// Token comes from the client app
		System.out.println(FirebaseAuth.getInstance().verifyIdToken(token));
		return FirebaseAuth.getInstance().verifyIdToken(token);
	}

	@PreDestroy
	public void destroy() {
		FirebaseApp.getInstance().delete();
	}
}
