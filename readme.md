Money transfer RESTful API
=
Java implementation of a RESTful API.

#### How to build and run

``` 
mvn package
cd target
java -jar easy-money-api-embedded.jar

```
It starts on localhost with default port 8080. It is possible to change default port with set enviroment variale PORT.

Here is the list of available endpoints:

* **Create new wallet**
```
    http://localhost:8080/api/wallet (POST)
```
* **Get wallet balance**
```
    http://localhost:8080/api/wallets/{id}/balance (GET)
```
* **Recharge wallet**
```
    http://localhost:8080/api/wallets/{id}/recharging/{amount} (GET)
```
* **Transfer money**
```
    http://localhost:8080/api/wallets/{id}/payment (POST)
```
Request body:
```json 
{"walletId":2,"amount":"100"}
```



