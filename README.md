## Customer Reward
This project is a Spring Boot application that provides a RESTful API to calculate reward points for customers based on their transactions. The reward points are calculated according to the following rules:

2 points for every dollar spent over $100 in each transaction.
1 point for every dollar spent between $50 and $100 in each transaction.
No points are awarded for amounts less than $50.
For example, for a $120 purchase:

2 x $20 = 40 points (for the amount above $100)
1 x $50 = 50 points (for the amount between $50 and $100)
Total: 90 points.

## Features
- Calculates reward points for each transaction.
- Supports calculation of total points earned for a given month.

## REST API Endpoints

## Total Reward Points: Calculates total points for multiple transactions.
Endpoint - http://localhost:8081/CalculateReward/CustomerTotalPoints?customerId=2
Method: GET 

<br>
Response: <br>
```json
{
    "customerId": 2,
    "customerName": "Tina",
    "transactions": [
        {
            "id": 1,
            "amount": 120.0,
            "transDate": "2025-01-15",
            "customerId": 2
        },
        {
            "id": 2,
            "amount": 70.0,
            "transDate": "2025-02-18",
            "customerId": 2
        },
        {
            "id": 5,
            "amount": 250.0,
            "transDate": "2025-02-20",
            "customerId": 2
        }
    ],
    "totalPoints": 460
}
```

## Monthly Reward Points: Calculates total points for the specified month.
Endpoint - http://localhost:8081/CalculateReward/perMonth?customerId=2&month=02&year=2025
Method: GET 

<br>
Response: <br>
```json
{
    "customerId": 2,
    "customerName": "Tina",
    "transactions": [
        {
            "id": 2,
            "amount": 70.0,
            "transDate": "2025-02-18",
            "customerId": 2
        },
        {
            "id": 5,
            "amount": 250.0,
            "transDate": "2025-02-20",
            "customerId": 2
        }
    ],
    "totalPoints": 370
}
```
## Request Steps
### Cloning:

	Clone the repository from GitHub.

### Run the application:

	Navigate to the project directory and run mvn spring-boot:run.

### Make a Request:

	Use Postman or any HTTP client to send the GET /CalculateReward/ request.

## Technologies Used
- Java 17
- Spring Boot
- Maven
- JUnit (for unit tests)
- mysql Database