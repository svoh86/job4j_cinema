# Приложение "Кинотеатр"

[![Java CI with Maven](https://github.com/svoh86/job4j_cinema/actions/workflows/maven.yml/badge.svg)](https://github.com/svoh86/job4j_cinema/actions/workflows/maven.yml)

+ [О проекте](#О-проекте)
+ [Технологии](#Технологии)
+ [Взаимодействие с приложением](#Взаимодействие-с-приложением)
+ [Контакты](#Контакты)

## О проекте

Web-приложение "Кинотеатр". Сервис для покупки билетов на конкретный фильм. В нем есть возможность выбрать сеанс, ряд и
место. Выбранные места отображаются пользователю. При нажатии кнопки "Купить" переходим в корзину. Если места уже
заняты, то купить их не получится. Будет выведен список мест, которые не удалось купить. Для использования требуется
регистрация.

## Технологии

+ **Maven 3.8**
+ **Spring Boot**
+ **HTML**, **BOOTSTRAP**, **Thymeleaf**
+ **JDBC**, **PostgreSQL 14**
+ **Тестирование:** **Mockito**, **Liquibase**, **H2**, **AssertJ**
+ **Java 17**
+ **Checkstyle**

## Взаимодействие с приложением
Начальная страница
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/start.png)

Регистрация пользователя
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/registration.png)

Неудачная регистрация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/failRegistration.png)

Удачная регистрация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/successRegistration.png)

Авторизация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/login.png)

Неудачная авторизация
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/failLogin.png)

Выбор места
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/choiceSeats.png)

Корзина
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/basket.png)

Предупреждение, что эти места уже заняты
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/failTicket.png)

Сообщение, что билеты куплены
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/successTicket.png)

Сообщение, что билеты не выбраны
![alt text](https://github.com/svoh86/job4j_cinema/blob/master/src/main/java/ru/job4j/cinema/img/needsTicket.png)

## Контакты

Свистунов Михаил Сергеевич

[![Telegram](https://img.shields.io/badge/Telegram-blue?logo=telegram)](https://t.me/svoh86)

Email: sms-86@mail.ru
