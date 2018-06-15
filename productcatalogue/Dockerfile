FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY productcatalogue .

RUN mkdir -p /app/target
RUN chown -R 1000:1000 /app
RUN chmod -R 777 /app
USER test

CMD ["mvn", "clean", "install"]
