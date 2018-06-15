FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY shopfront .

RUN chown -R 1000:1000 /app
RUN chmod -R 777 /app

USER test

CMD ["mvn", "clean", "install"]
