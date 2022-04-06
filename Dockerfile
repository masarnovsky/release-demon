FROM openjdk:11
COPY build/libs/*-SNAPSHOT.jar release-demon.jar
ENTRYPOINT ["java","-jar","/release-demon.jar"]