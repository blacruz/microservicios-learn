FROM openjdk:11
COPY "target/config-service*.jar" "config-service.jar"
EXPOSE 8888
ENTRYPOINT ["java","-jar","-DskipTests","config-service.jar"]