FROM openjdk:11
COPY ./target/buyhelper-0.0.1-SNAPSHOT.jar buyhelper-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","buyhelper-0.0.1-SNAPSHOT.jar"]