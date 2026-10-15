FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Credentials for GitHub Packages (Render passes service env vars as build args)
ARG BIDDER_USERNAME
ARG BIDDER_GITHUB_TOKEN
ENV BIDDER_USERNAME=$BIDDER_USERNAME \
    BIDDER_GITHUB_TOKEN=$BIDDER_GITHUB_TOKEN

COPY . .
RUN chmod +x gradlew \
    && ./gradlew bootJar --no-daemon \
    && cp "$(ls build/libs/*.jar | grep -v plain)" app.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
