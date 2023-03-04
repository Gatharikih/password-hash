# syntax=docker/dockerfile:1
#base img
FROM eclipse-temurin:17-jdk-jammy

# image’s working directory
WORKDIR /

#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
ADD /target/auth-abe-pay.jar /target/auth-abe-pay.jar

EXPOSE 8081/tcp
LABEL password-auth_version="1.0"

# dependencies on the image
#RUN ./mvnw dependency:resolve

# add our source code into the image
#COPY src ./src

# command to run when our image is executed inside a container
#CMD ["./mvnw", "spring-boot:run"]
CMD java -jar /target/auth-abe-pay.jar