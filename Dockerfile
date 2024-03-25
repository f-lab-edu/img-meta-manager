FROM openjdk:17.0.2-jdk

ENV APP_HOME=/apps

ENV SPRING_PROFILES_ACTIVE=locals

ARG JAR_FILE_PATH=build/libs/ImageTagManagement-0.0.1-SNAPSHOT.jar

COPY aws root/.aws

WORKDIR $APP_HOME

COPY $JAR_FILE_PATH apps.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "apps.jar"]