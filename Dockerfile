FROM amazoncorretto:11 as build
# RUN mkdir /code
# WORKDIR /code
ADD . .
RUN chmod +x mvnw
RUN ./mvnw clean install -Dmaven.test.skip=true
#  -Dhttps.protocols=TLSv1.2

FROM amazoncorretto:11
RUN mkdir /jar
WORKDIR /jar

COPY --from=build /sicredi-api/target/sicredi-api.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","sicredi-api.jar"]
