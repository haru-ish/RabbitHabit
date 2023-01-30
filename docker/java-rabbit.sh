#!/usr/bin/env bash

java -jar /jar/RabbitTracker-0.0.1-SNAPSHOT.jar \
    --server.port=4278 \
    --spring.web.resources.static-locations=classpath:/frontend/ \
    --spring.datasource.driver-class-name=org.postgresql.Driver \
    --spring.datasource.url="jdbc:postgresql://localhost:5432/postgres?serverTimezone=UTC" \
    --spring.datasource.username="postgres" \
    --spring.datasource.password="postgres" \
    --spring.sql.init.encoding="UTF-8" \
    --spring.security.oauth2.resourceserver.jwt.jwk-set-uri="https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com" \
    --spring.security.oauth2.resourceserver.jwt.issuer-uri="https://securetoken.google.com/$FIREBASE_ID"
