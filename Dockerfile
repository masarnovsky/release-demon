FROM openjdk:11
COPY jar/release-demon.jar.jar release-demon.jar
ENTRYPOINT ["java","-jar","/release-demon.jar"]