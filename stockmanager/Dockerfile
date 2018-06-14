FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY stockmanager  .

RUN chown -R 1001:125 /app

USER test

CMD ["mvn", "clean", "install"]
