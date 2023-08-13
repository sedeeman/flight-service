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

#### Add New Flight
This API endpoint allows you to add a new flight to the database.

URL: [http://localhost:3000/api/flight-service](http://localhost:3000/api/flight-service)
Method: POST
Request Body: A JSON object representing the details of the new flight.
`{
"flightNumber": "AC12791",
"originLocation": "YVR3",
"destinationLocation": "Vancouver",
"departureTime": "2023-08-06T10:00:00",
"arrivalTime": "2023-08-06T11:30:00",
"flightType": "INBOUND",
"terminalGate": "A14",
"status": "DEPARTURE"
}`

Response: Returns a success message along with the details of the newly added flight.

`{
"code": 201,
"status": "Created",
"message": "Successfully added a new Flight",
"data": {
"flightId": 10,
"flightNumber": "AC12791",
"originLocation": "YVR3",
"destinationLocation": "Vancouver",
"flightType": "INBOUND",
"terminalGate": "A14",
"arrivalTime": "2023-08-06T11:30:00",
"departureTime": "2023-08-06T10:00:00",
"status": "DEPARTURE",
"delayed": false
}
}`

#### Get All Flights
This API endpoint allows you to retrieve a list of all flights.

URL: [http://localhost:3000/api/flight-service](http://localhost:3000/api/flight-service)
Method: GET
Response: Returns a JSON array containing the list of flights.
`{
"code": 200,
"status": "OK",
"message": "Successfully retrieved flights",
"data": [
{
"flightId": 1,
"flightNumber": "AC126",
"originLocation": "Galle",
"destinationLocation": "Vancouver",
"flightType": "OUTBOUND",
"terminalGate": "A13",
"arrivalTime": "2023-08-06T11:30:00",
"departureTime": "2023-08-06T10:00:00",
"status": "SCHEDULED",
"delayed": true
},
{
"flightId": 2,
"flightNumber": "AC127",
"originLocation": "Galle",
"destinationLocation": "Vancouver",
"flightType": "OUTBOUND",
"terminalGate": "A13",
"arrivalTime": "2023-08-06T11:30:00",
"departureTime": "2023-08-06T10:00:00",
"status": "DEPARTURE",
"delayed": true
}
]
}`

#### Search Flight
This API endpoint allows you to search flights

URL: [http://localhost:3000/api/flight-service/search?flightNumber=Beoing7372](http://localhost:3000/api/flight-service/search?flightNumber=Beoing7372)
Method: GET
Query Params = flightNumber,originLocation,destinationLocation, flightType, terminalGate, arrivalTime, departureTime, status


Response: Returns a success message along with the details of searched flight.
`{
"code": 200,
"status": "OK",
"message": "Successfully searched flights",
"data": [
{
"flightId": 2,
"flightNumber": "Beoing7372",
"originLocation": "Calgary",
"destinationLocation": "Vancouver",
"flightType": "INBOUND",
"terminalGate": "Gate2",
"arrivalTime": "2023-08-13T06:21:25",
"departureTime": "2023-08-13T08:21:25",
"status": "SCHEDULED",
"delayed": true
}
]
}`

#### Update Flight Status and Fire Event to RabbitMQ Message Queue

##### PATCH http://localhost:3000/api/flight-service/{flightId}

In this update, the flight status has been modified, triggering an event that is sent to the RabbitMQ message queue. The Notification Service now listens for these status changes and sends out email notifications to subscribers, tailored to their preferences.

Request body example:

`{
"flightNumber":"FL123",
"status":"arrival"
}`

### MySQL Database
The Flight Service Microservice uses an MySQL Database You can modify the database connection properties in the application.properties file if you want to switch to a different database.

### Swagger2 Documentation
The API endpoints are documented using Swagger2. To access the documentation, launch the application and navigate to the following URL in your web browser:

[http://localhost:3000/api/swagger-ui/index.html#/](http://localhost:3000/api/swagger-ui/index.html#/)

#### Testing
To run the unit tests for the Flight Notification Service, use the following command:

`./mvnw test`
