FROM maven:3.6.3-jdk-11

EXPOSE 8300
COPY . /image-service
WORKDIR /image-service

RUN sed -i 's/spring.rabbitmq.host=localhost/spring.rabbitmq.host=licenta-rmq/g' src/main/resources/application.properties
RUN sed -i 's/localhost:3306/image-service-mysql:3306/g' src/main/resources/application.properties
RUN cat src/main/resources/application.properties

RUN mvn clean package spring-boot:repackage -DskipTests
CMD java -jar target/image-service-0.0.1-SNAPSHOT.jar

#docker build -t image-service:0.1 .
#docker run -p 8300:8300 --network licenta-network --name image-service-container --rm image-service:0.1
