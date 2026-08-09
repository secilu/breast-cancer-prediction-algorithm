FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN javac --add-modules jdk.httpserver *.java

EXPOSE 10000

CMD ["java", "--add-modules", "jdk.httpserver", "CancerServer"]
