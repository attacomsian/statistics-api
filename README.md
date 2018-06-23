# Statistics API
The main use case for this API is to calculate realtime statistic from the last 60 seconds. There are two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

## API Endpoints

### 1. POST /transactions
Every time a new transaction happened, this endpoint is called.

**Request**
```http
POST /transactions HTTP/1.1
Host: localhost:8080
Content-Type: application/json
{
  "amount": 9.99, 
  "timestamp": 1529750938000
}
```

**Response**

Returns empty body with either 201 or 204.
* 201 - in case of success
* 204 - if transaction is older than 60 seconds
```http
HTTP/1.1 201 Created
Content-Type: application/json
```

### 2. GET /statistics
It returns the statistic of transactions happened in the last 60 seconds.

**Request**
```http
GET /statistics HTTP/1.1
Host: localhost:8080
```

**Response**
```http
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
{
    "sum": 1179.89,
    "avg": 168.5557142857143,
    "max": 999.5,
    "min": 0.99,
    "count": 7
}
```

## Usage
In order to build this application, run the following gradle command:
```ssh
gradlew build
```

To run this applicatin, use the following gradle command:
```ssh
gradlew bootRun
```

## License
MIT