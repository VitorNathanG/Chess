FROM openjdk:15-alpine
WORKDIR /

ADD ChessBrain.jar ChessBrain.jar
CMD java -jar ChessBrain.jar