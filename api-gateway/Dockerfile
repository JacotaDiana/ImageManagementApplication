FROM maven:3.6.3-jdk-11

EXPOSE 8100
COPY . /api-gateway
WORKDIR /api-gateway
RUN sed -i 's/localhost:3306/api-gateway-mysql:3306/g' src/main/resources/application.properties
RUN cat src/main/resources/application.properties

RUN mvn clean package spring-boot:repackage -DskipTests
CMD java -jar target/api-gateway-0.0.1-SNAPSHOT.jar

#docker build -t api-gateway:0.2 .
#docker run -p 8100:8100 --network licenta-network --name api-gateway-container --rm api-gateway:0.2
