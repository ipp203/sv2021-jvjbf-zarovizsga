FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/final-exam-0.0.1-SNAPSHOT.jar /opt/app/finalexam.jar
CMD ["java","-jar","opt/app/finalexam.jar"]