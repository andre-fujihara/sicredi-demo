FROM amazoncorretto:11 as build

ADD . .
RUN chmod +x mvnw
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM amazoncorretto:11
RUN mkdir /jar
WORKDIR /jar

COPY --from=build /target/sicredi-api.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","sicredi-api.jar"]