FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/product-parser.jar product-parser.jar
ENTRYPOINT ["java","-jar","/product-parser.jar"]