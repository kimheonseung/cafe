FROM openjdk:17-jdk-slim
LABEL authors="kimheonseung"

ARG PROFILE
ARG APP_NAME=menu-api
ARG BUILD_DIR=build/libs
ARG SRC_DIR=/opt/menu-api
ARG JAR_FILE=menu-api.jar
ARG APP_USER=appuser

# spring.application
ENV JAVA_TOOL_OPTIONS "-Dspring.profiles.active=${PROFILE} -DappName=${APP_NAME}"
# GC
ENV JAVA_TOOL_OPTIONS "${JAVA_TOOL_OPTIONS} -XX:+UseG1GC"
# log4j
ENV JAVA_TOOL_OPTIONS "${JAVA_TOOL_OPTIONS} -Dlog4j2.formatMsgNoLookups=true"
# encoding
ENV JAVA_TOOL_OPTIONS "${JAVA_TOOL_OPTIONS} -Dfile.encoding=UTF-8 -Dfile.client.encoding=UTF-8 -Dclient.encoding.override=UTF-8"
# Memory 70%
ENV JAVA_TOOL_OPTIONS "${JAVA_TOOL_OPTIONS} -XX:InitialRAMPercentage=70.0 -XX:MaxRAMPercentage=70.0"

RUN addgroup --group --gid 1001 ${APP_USER} && adduser --system --uid 1001 --gid 1001 ${APP_USER}
COPY ${BUILD_DIR}/${JAR_FILE} ${SRC_DIR}/${JAR_FILE}
RUN mkdir -p ${SRC_DIR} && chown -R ${APP_USER}:${APP_USER} ${SRC_DIR}

WORKDIR ${SRC_DIR}
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "menu-api.jar"]
