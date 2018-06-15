FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN mkdir /app

WORKDIR /app

COPY shopfront .

RUN chown -R 1000:1000 /app


RUN chmod +x /app/fix.sh

USER test

CMD ["mvn", "clean", "install"]


# ENTRYPOINT [ "/app/fix.sh" ]