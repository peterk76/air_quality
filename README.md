# air quality demo project

## backend

- java 17
- spring boot
- postgressql (scripts: script.sql)
- configuration (in file application.yaml)
```sh
spring:
  application:
    name: quality
  datasource:
    url: jdbc:postgresql://localhost:5432/<db name>
    username: <db user>
    password: <db password>
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
report:
  directory: <monthly raport directory>
```
- default users: admin/admin, admin1/admin1, admin2/admin2

## frontend

- frontend\air_quality
- javascript + Lit
- run with Vite - site http://localhost:5173/
