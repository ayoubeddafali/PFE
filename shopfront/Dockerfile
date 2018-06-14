FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY shopfront .

RUN chown -R 1000:1000 /app

USER root

CMD ["mvn", "clean", "install"]
