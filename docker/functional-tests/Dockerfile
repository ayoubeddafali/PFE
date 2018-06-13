FROM ayoubensalem/spring:dev

MAINTAINER ayoubensalem

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN chown -R 1001:125 /app

RUN chmod +x ./wait-for-it.sh
RUN chmod +x ./all.sh

USER test

ENTRYPOINT ["./all.sh"]

CMD ["mvn",  "test"]
