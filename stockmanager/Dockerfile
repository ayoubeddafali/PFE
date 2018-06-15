FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY stockmanager  .

RUN chown -R 1000:1000 /app

USER test 

CMD ["mvn", "clean", "install"]
