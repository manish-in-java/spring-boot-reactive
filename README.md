# Spring WebFlux with Angular

This application has a Spring Boot based backend that uses Spring WebFlux to
provide a reactive REST API, and an Angular based frontend.

## Running the application in dev mode

First start the Spring Boot backend using:
```
mvn spring-boot:run
```

The backend should then be available on http://localhost:8080. When run in the
dev mode, the Angular application is not bundled with the Spring Boot
application. It can be run using:
```
ng serve
```

The Angular application should then be available on http://localhost:4200.

## Packaging and running the application

The application can be packaged using `mvn package -Pproduction`. It bundles
the Angular code with the Spring Boot application and produces a standard
Boot JAR file.
