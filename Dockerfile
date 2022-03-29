FROM openjdk:11
COPY build/libs/release-demon-0.0.1-SNAPSHOT.jar release-demon.jar
ENTRYPOINT ["java","-jar","/release-demon.jar"]