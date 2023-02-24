# syntax=docker/dockerfile:1
#base img
FROM eclipse-temurin:17-jdk-jammy
# image’s working directory
WORKDIR /

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# dependencies on the image
RUN ./mvnw dependency:resolve
# add our source code into the image
COPY src ./src
# command to run when our image is executed inside a container
CMD ["./mvnw", "spring-boot:run"]