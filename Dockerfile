FROM openjdk:15-alpine
WORKDIR /
CMD javac -cp src/main/java -d target src/main/java/Main.java
CMD jar -c --file ChessBrain.jar --manifest MANIFEST.MF -C target/  .
ADD ChessBrain.jar ChessBrain.jar
CMD java -jar ChessBrain.jar