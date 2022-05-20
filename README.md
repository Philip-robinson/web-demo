# WEB DEMO

This is a demonstration of a simple web interface using Thymeleaf that accesses data over an http api call to
the project api-demo

## Compile

This is intended to be compiled using java 17 at a minimum

```
$ mvn clean install
```

## Run

In the target directory

```
$ java -jar web-demo*.jar
```
## Configuration

Configuration is in the internal __application.properties__ file

server.port is set to 8082

It is assumed that api-demo will be available on http://localhost:8081

