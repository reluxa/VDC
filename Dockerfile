FROM anapsix/alpine-java:8_server-jre

ENV PATH /usr/local/tomee/bin:$PATH
ENV SQUASH_DB /squash.db

RUN mkdir -p /usr/local/tomee

WORKDIR /usr/local/tomee

RUN apk add --no-cache curl \
    && curl -fSL http://apache.rediris.es/tomee/tomee-1.7.5/apache-tomee-1.7.5-webprofile.tar.gz -o tomee.tar.gz \
    && tar -zxf tomee.tar.gz \
    && mv apache-tomee-webprofile-1.7.5/* /usr/local/tomee \
    && rm -Rf apache-tomee-webprofile-1.7.5 \
    && rm bin/*.bat \
    && rm tomee.tar.gz* \
    && rm -r /usr/local/tomee/webapps/ROOT


COPY target/example.war /usr/local/tomee/webapps/ROOT.war


EXPOSE 8080
CMD ["catalina.sh", "run"]