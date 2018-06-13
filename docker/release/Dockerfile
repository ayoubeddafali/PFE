FROM openjdk:8-jre

MAINTAINER ayoubensalem

RUN mkdir /app
WORKDIR /app

ADD ../../productcatalogue/target/*.jar catalog.jar
ADD ../../shopfront/target/*.jar front.jar
ADD ../../product-catalogue.yml app-config.yml
ADD ../../stockmanager/target/*.jar stock.jar

EXPOSE 8030
EXPOSE 8020
EXPOSE 8010

COPY scripts/start.sh /app/start.sh
RUN chmod +x start.sh


