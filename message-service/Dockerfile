FROM maven:3.6.3-jdk-11

EXPOSE 8400
COPY . /message-service
WORKDIR /message-service

RUN sed -i 's/spring.rabbitmq.host=localhost/spring.rabbitmq.host=licenta-rmq/g' src/main/resources/application.properties
RUN sed -i 's/localhost:3306/message-service-mysql:3306/g' src/main/resources/application.properties
RUN cat src/main/resources/application.properties

RUN mvn clean package spring-boot:repackage -DskipTests
CMD java -jar target/message-service-0.0.1-SNAPSHOT.jar

#docker build -t message-service:0.1 .
#docker run -p 8400:8400 --network licenta-network --name message-service-container --rm message-service:0.1
