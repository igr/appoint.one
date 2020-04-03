# Build the Kotlin
# see https://github.com/ktorio/ktor/issues/1744
FROM gradle:6.3.0-jdk8 AS builder
ADD . /app
WORKDIR /app/backend
#RUN ./gradlew distTar
RUN ./gradlew build -x test

# build stage (vue)
FROM node:13-alpine3.10 as build-stage
COPY --from=builder /app/frontend ./
RUN yarn install
RUN yarn build

# production stage
FROM nginx:stable-alpine as production-stage
RUN apk add openjdk8
#COPY --from=builder /app/backend/build/distributions ./server
#RUN tar -xvf /server/appoint1-shadow.tar
COPY --from=builder /app/backend/ ./server
COPY --from=build-stage /dist /usr/share/nginx/html
EXPOSE 80
ADD /docker/start.sh /
CMD ["/start.sh"]