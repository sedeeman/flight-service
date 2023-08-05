# Flight Service Microservice

### The Flight Service Microservice provides  REST APIs to retrieve a list of all flights and add new flights.

### Getting Started

#### Clone the repository:
`git clone https://github.com/sedeeman/flight-service.git`
`cd flight-service`

#### Build the project:
`./mvnw clean package`

#### Run the application:
`java -jar target/flight-service.jar`

### API Endpoints

#### Get All Flights
This API endpoint allows you to retrieve a list of all flights.

URL: [http://localhost:3000/api/flights](http://localhost:3000/api/flights)
Method: GET
Response: Returns a JSON array containing the list of flights.
`{
"code": 200,
"status": "OK",
"message": "Successfully retrieved flights",
"data": [
{
"flightId": 1,
"flightNumber": "ABC1235",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "10",
"airportName": "Los Angeles International Airport",
"location": "Los Angeles, CA",
"status": null,
"delayed": false
}
]
}`

#### Add New Flight
This API endpoint allows you to add a new flight to the database.

URL: [http://localhost:3000/api/flights](http://localhost:3000/api/flights)
Method: POST
Request Body: A JSON object representing the details of the new flight.
`{
"flightNumber": "ABC1265",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "10",
"airportName": "Los Angeles International Airport",
"location": "Los Angeles, CA"
}`

Response: Returns a success message along with the details of the newly added flight.
`{
"code": 201,
"status": "Created",
"message": "Successfully Add a new product",
"data": {
"flightId": 3,
"flightNumber": "ABC1265",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "10",
"airportName": "Los Angeles International Airport",
"location": "Los Angeles, CA",
"status": null,
"delayed": false
}
}`

#### Search Flight
This API endpoint allows you to search flights

URL: [http://localhost:3000/api/flights?flightNumber=ABC123](http://localhost:3000/api/flights?flightNumber=ABC123)
Method: GET

Response: Returns a success message along with the details of the newly added flight.
`{
"code": 200,
"status": "OK",
"message": "Successfully retrieved flights",
"data": [
{
"flightId": 1,
"flightNumber": "ABC123",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "10",
"airportName": "Los Angeles International Airport",
"location": "Los Angeles, CA",
"status": "DEPARTURE",
"delayed": false
}
]
}`

### H2 Database
The Flight Service Microservice uses an H2 in-memory database by default. You can modify the database connection properties in the application.properties file if you want to switch to a different database.

### Swagger2 Documentation
The API endpoints are documented using Swagger2. To access the documentation, launch the application and navigate to the following URL in your web browser:

[http://localhost:3000/api/swagger-ui/index.html#/](http://localhost:3000/api/swagger-ui/index.html#/)


#### Testing
To run the unit tests for the Flight Notification Service, use the following command:

`./mvnw test`
