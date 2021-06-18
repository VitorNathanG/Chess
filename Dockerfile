FROM openjdk:15-alpine
COPY src/main/java/*.java src/main/java/
COPY MANIFEST.MF MANIFEST.MF
RUN javac -cp src/main/java -d target src/main/java/Main.java
RUN jar -c --file ChessBrain.jar --manifest MANIFEST.MF -C target/  .
ENTRYPOINT java -jar ChessBrain.jar