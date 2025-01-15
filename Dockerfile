FROM eclipse-temurin:21-jre-alpine

RUN adduser -u 1000 spring -s /bin/sh -D spring

USER spring

WORKDIR /app

