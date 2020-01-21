# RESTful API for money transfers between accounts.

## Technology stack
- Java 8
- Jetty
- Jersey
- Jersey's HK2
- JUnit
- Mockito
- httpclient

## How to run
- java -jar .\target\money_transfers-1.0-SNAPSHOT.jar

## API
- GET http://localhost:8080/account/{accountId}
- POST http://localhost:8080/account/create (Amount in body as String)
- POST http://localhost:8080/account/deposit/{accountId} (Amount in body as String)
- POST http://localhost:8080/account/withdraw/{accountId} (Amount in body as String)
- DELETE http://localhost:8080/account/delete/{accountId}
- POST http://localhost:8080/transfer/from/{fromId}/to/{toId} (Amount in body as String)

## Integration tests
com.piv.money.transfers.AccountResourceIntegrationTest - testing account API
com.piv.money.transfers.TransferResourceIntegrationTest - testig transfer API
