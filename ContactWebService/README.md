# Веб-сервис по управлению контактами

<u>Начало работы</u>

Для работы чата используется БД PostgreSQL, которая развернута в Docker-контейнере.</br>

<u>Запуск приложения</u>

1. Запустите Docker-контейнер, последовательно выполнив команды:
```
    cd docker
    docker compose up
```
По умолчанию, для доступа к БД используются следующие данные:
```
    url: jdbc:postgresql://localhost:5432/contacts
    username: postgres
    password: postgres
```
2. Запустите java/com/example/springApplication/Application.java
3. Открыть в браузере веб-приложение по адресу http://localhost:8080/contacts

