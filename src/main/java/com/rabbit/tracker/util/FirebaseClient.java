package com.rabbit.tracker.util;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PreDestroy;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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

	@PreDestroy
	public void destroy() {
		FirebaseApp.getInstance().delete();
	}
}
