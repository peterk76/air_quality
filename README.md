# air quality demo project

## backend

- java 17
- spring boot
- postgressql (scripts: script.sql)
- configuration (in file application.yaml)
```
spring:
  application:
    name: quality
  datasource:
    url: jdbc:postgresql://<host>:<port>/<db name>
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
- run with Vite (starting from scratch)
```
frontend>npx vite
frontend>ctrl + c
frontend>npm init --yes
frontend>npm create vite@latest
  (air_quality)
  (Lit)
  (javascript)
(copy air_quality from GIT to new created air_quality)
frontend\air_quality>npm install
frontend\air_quality>npm run dev
```
- web site starting http://localhost:5173/
