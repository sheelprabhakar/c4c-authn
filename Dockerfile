FROM openjdk

COPY build/libs/c4c-authn-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]