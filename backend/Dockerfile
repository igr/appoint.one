FROM gradle:6.3.0-jdk8
ADD . /app
WORKDIR /app
RUN ./gradlew build -x test shadowJar

EXPOSE 80
CMD ["java", "-jar", "build/libs/appoint1-all.jar"]
