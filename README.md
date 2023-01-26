# Rabbit Habit
![RabbitHabit-Logo](https://github.com/haru-ish/RabbitHabit/blob/master/frontend/src/assets/images/rabbit.svg)

## Introduction
**Rabbit Habit** is an app for recording daily mood and habits.

Recording your mood on a daily basis can help you to understand your current mental state.

This app will support you to create habits that will keep you in good mental shape.

Record whether you have done the good habits along with how you feel today!

https://user-images.githubusercontent.com/108800859/198257732-0e9983b7-6a5e-4313-bc8f-0a6deeb860b7.mp4



https://user-images.githubusercontent.com/108800859/198259491-639cf6cd-8112-4cb0-9b42-5a476234a1dc.mp4



We hope **Rabbit Habit** will be useful to you take a moment to reflect on how you felt today and what you did to make yourself happy. :green_heart:

## Demo
https:// XXXXXXXXXXXX

## Run your own server

### Preparation and Installation
1. Clone Repository
```shell
$ git clone https://github.com/haru-ish/RabbitHabit.git
  
$ cd RabbitHabit
```
2. Create firebase project

3. Download service account file from firebase at project settings -> service account -> generate new key \
   Add this file as service-account-file.json to classpath "src/main/resources"

4. Add config from firebase at project-settings -> general -> your apps -> SDK setup and configuration -> Config to "frontend/  src/firebaseConfig.js"

5. Setup postgresql with user `rabbitdb` and database `rabbitdb`

6. Import schema with `psql -U rabbitdb rabbitdb < schema.sql`

7. Install Maven: `$ mvn install`

### Usage
Execute the compiled jar file to start the server with custom parameters ( the frontend is included in the jar and served automatically ):
```shell
$ java -jar target/RabbitTracker-0.0.1-SNAPSHOT.jar \
   --server.port=XXXX \
   --spring.web.resources.static-locations=classpath:/frontend/ \
   --spring.datasource.driver-class-name=org.postgresql.Driver \
   --spring.datasource.url="jdbc:postgresql://localhost:5432/rabbitdb?serverTimezone=UTC" \
   --spring.datasource.username="rabbitdb" \
   --spring.datasource.password="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" \
   --spring.sql.init.encoding="UTF-8" \
   --spring.security.oauth2.resourceserver.jwt.jwk-set-uri="https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com" \
   --spring.security.oauth2.resourceserver.jwt.issuer-uri="https://securetoken.google.com/XXXX-XXXXX"
```
Accese to front-end Web Page:
```
localhost:XXXX
```