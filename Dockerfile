FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/transfer-0.0.1-SNAPSHOT.jar transfer.jar
RUN bash -c 'touch /transfer.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms2G", "-Xmx2G","-jar","/transfer.jar"]