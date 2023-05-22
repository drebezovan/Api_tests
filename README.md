# MTS_Credit_App_Tests
MTS_Credit_App_Tests - это тесты для сервиса подачи заявок на кредит.

## Установка
- Развернуть PostgreSQL в соответствии с инструкцией разработчика (см. ниже "MTS_Credit_App").
- Создать базу данных с названием "MTS_Credit_App_TESTS" с пользователем "user" и паролем "1234".

## Запуск проекта
Запустить тестируемое приложение нужно при помощи следующей команды:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

## Причины установки MTS_Credit_App_Tests
Данный проект запускается для того, чтобы сократить время запуска сервера и начала его работы, за которые отвечают переменные initialDelay и fixedRate,
которые в проекте разработчика захардкожены (см. src/main/java/service/impl/ОщиServiceImpl.class).
В проекте "MTS_Credit_App_Tests" эти значения вынесены в переменные в файл "application-test.yaml".

Также в проекте разработчика захардкожено время ожидания обновления статуса заявки и установлено значение 2 минуты (src/main/java/service/impl/CreditUserServicesImpl.class),
для сокращения времени выполнения тестов это значение вынесено в переменную waitingTime в файл "application-test.yaml", в котором его можно менять.


















































# MTS_Credit_App
 MTS_Credit_App - это сервис, отвечающий за обработку кредитных заявок.

## Установка
Для развертки бд я использовал PostgreSQL. Поэтому перед тестированием необходимо:
- Зайти на официальный сайт PostgreSQL и загрузить установщик в соответсивии с вашей системой: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- После установки, создайте пользователя с логином "user" и паролем: "1234". Далее для этого пользователя необходимо создать базу данных с названием "MTS_Credit_App"

## Как запустить проект

Необходимо написать следующие команды, находясь в директории проекта

```bash
mvn clean install

./mvnw package
java -jar target/credit-app-0.0.1-SNAPSHOT.jar 
```

## API
1. Получение тарифов
```
localhost:8080/loan-service/getTariffs
```
2. Подача заявки на кредит
```
localhost:8080/loan-service/order
```
http body (пример)
```
{
    "userId": 123456,
    "tariffId": 3
}
```
3. Получение статуса заявки
```
localhost:8080/loan-service/getStatusOrder?orderId=24f35741-df87-47fb-8bd6-b927a720018
```
4. Удаление заявки
```
localhost:8080/loan-service/deleteOrder
```
http body (пример)
```
{
    "userId": 123456,
    "orderId": "b12b25e9-f6be-4c28-9140-ed137bd1f630"
}
```
