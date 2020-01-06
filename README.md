# spring-async 
Spring Boot application example with scheduling, asynchronous execution support, custom asynchronous exception handler 
and separate database transactions. 

## 1. Install&Run

### Used technologies

Kotlin 1.3.61

Gradle 5.5

Spring Boot 2.2.2

H2 database 1.4.200

### Run as a JAR file

Build:

`gradlew clean build`

Run the application:

`java -jar build/libs/spring-async-0.0.1-SNAPSHOT.jar`


## 2. Insert test data
Run this SQL command in h2 console (http://localhost:8080/h2-console)
```sql
INSERT INTO DATA (COUNTER,STATE) 
SELECT 0,'TODO' from generate_series(1,100);
```

## 3. Details
This application creates a H2 in-memory database. The scheduler collects all data rows with 'TODO' state in every minute, 
changes their state to 'DONE' in 8 thread, all in separate transactions. If there is an exception during the process, it throws a 
custom AsyncException, and increases the value of the COUNTER row by 1.

### Authors
Daniel Hajdu-Kis - dhajdukis
