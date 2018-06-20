FROM ayoubensalem/spring

MAINTAINER ayoubensalem

RUN apt-get update  \
&& apt-get install -y nginx supervisor software-properties-common apt-transport-https \
&& echo "deb https://dl.bintray.com/sbt/debian /" >> /etc/apt/sources.list.d/sbt.list \
&& apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 \
&& apt-get update \
&& apt-get install -y sbt \
&& apt-get remove -y --purge software-properties-common \
&& apt-get -y autoremove \
&& apt-get clean \
&& rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* \
&& echo "daemon off;"   >>   /etc/nginx/nginx.conf \
&& ln -sf /dev/stdout /var/log/nginx/access.log \
&& ln -sf /dev/stderr /var/log/nginx/error.log

RUN mkdir /app
WORKDIR /app

COPY . /app

EXPOSE 80

COPY default /etc/nginx/sites-available/default
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
RUN chmod +x start.sh

RUN chown -R 1000:1000 /app

USER root

ENTRYPOINT ["./start.sh"]



