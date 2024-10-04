FROM openjdk:21-jre
EXPOSE 8082
COPY target/ /application/
WORKDIR /application
CMD ["/bin/bash", "-c", "java -Xmx1024m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/dumps -jar *.jar"]
