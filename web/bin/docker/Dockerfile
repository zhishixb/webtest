FROM docker.m.daocloud.io/openjdk:17
LABEL maintainer="ThinkGem@163.com"
ENV TZ "Asia/Shanghai"
ENV LANG C.UTF-8
VOLUME /tmp
VOLUME /data

WORKDIR /app

#RUN mkdir WEB-INF
#ADD jeesite.lic ./WEB-INF
ADD ./maven/web.war ./app.war

#ENV JAVA_OPTS "$JAVA_OPTS -Xms256m -Xmx1024m"
ENV JAVA_OPTS "$JAVA_OPTS -Dspring.profiles.active=prod"

ENTRYPOINT if [ -f "app.war" ]; then jar -xvf app.war && rm app.war; \
           fi && cd WEB-INF && sh startup.sh && cd WEB-INF && sh startup.sh

EXPOSE 8980

#docker run -p 8980:8980 thinkgem/jeesite-web:5.10
