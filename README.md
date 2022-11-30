# Приложение "Кинотеатр"

[![Java CI with Maven](https://github.com/svoh86/job4j_cinema/actions/workflows/maven.yml/badge.svg)](https://github.com/svoh86/job4j_cinema/actions/workflows/maven.yml)

+ [О проекте](#О-проекте)
+ [Технологии](#Технологии)
+ [Требования к окружению](#Требования-к-окружению)
+ [Запуск проекта](#Запуск-проекта)
+ [Взаимодействие с приложением](#Взаимодействие-с-приложением)
+ [Контакты](#Контакты)

## О проекте

Web-приложение "Кинотеатр". Сервис для покупки билетов на конкретный фильм. В нем есть возможность выбрать сеанс, ряд и
место. Выбранные места отображаются пользователю. При нажатии кнопки "Купить" переходим в корзину. Если места уже
заняты, то купить их не получится. Будет выведен список мест, которые не удалось купить. Для использования требуется
регистрация.

## Технологии

+ **Maven 3.8**
+ **Spring Boot 2.7.5**
+ **HTML 5**, **BOOTSTRAP 5**, **Thymeleaf 3.0.15**
+ **JDBC**, **PostgreSQL 14**
+ **Тестирование:** **Mockito 4.0.0**, **Liquibase 3.6.2**, **H2 2.1.214**, **AssertJ 3.23.1**
+ **Java 17**
+ **Checkstyle 3.1.2**

## Требования к окружению
+ **Java 17**
+ **Maven 3.8**
+ **PostgreSQL 14**

## Запуск проекта
Перед запуском проекта необходимо настроить подключение к БД в соответствии с параметрами, 
указанными в src/main/resources/db.properties, или заменить на свои параметры.

Варианты запуска приложения:
1. Упаковать проект в jar архив (job4j_cinema/target/job4j_cinema-1.0.jar):
``` 
mvn package
``` 
Запустить приложение:
```
java -jar job4j_cinema-1.0.jar 
```
2. Запустить приложение:
```
mvn spring-boot:run
```

## Взаимодействие с приложением
Начальная страница
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/start.png)

Регистрация пользователя
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/registration.png)

Неудачная регистрация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/failRegistration.png)

Удачная регистрация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/successRegistration.png)

Авторизация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/login.png)

Неудачная авторизация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/failLogin.png)

Выбор места
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/choiceSeats.png)

Корзина
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/basket.png)

Предупреждение, что эти места уже заняты
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/failTicket.png)

Сообщение, что билеты куплены
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/successTicket.png)

Сообщение, что билеты не выбраны
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/img/needsTicket.png)

## Контакты

Свистунов Михаил Сергеевич

[![Telegram](https://img.shields.io/badge/Telegram-blue?logo=telegram)](https://t.me/svoh86)

Email: sms-86@mail.ru
