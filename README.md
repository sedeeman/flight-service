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

URL: [http://localhost:3000/api/flights](http://localhost:3000/api/flights)
Method: POST
Request Body: A JSON object representing the details of the new flight.
`{
"flightId": 1,
"flightNumber": "Boeing737",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "YVR3",
"airportName": "Vancourer International Airport",
"location": "Vancourer, BC",
"status": "departure",
"delayed": false
}`

Response: Returns a success message along with the details of the newly added flight.

`{
"code": 201,
"status": "Created",
"message": "Successfully Add a new Flight",
"data": {
"flightId": 1,
"flightNumber": "Boeing737",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "YVR3",
"airportName": "Vancourer International Airport",
"location": "Vancourer, BC",
"status": "departure",
"delayed": false
}
}`

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
"flightNumber": "Boeing737",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "YVR3",
"airportName": "Vancourer International Airport",
"location": "Vancourer, BC",
"status": "DEPARTURE",
"delayed": true
},
{
"flightId": 2,
"flightNumber": "Boeing747",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "INBOUND",
"airportCode": "YVR3",
"airportName": "Vancourer International Airport",
"location": "Vancourer, BC",
"status": "ARRIVAL",
"delayed": true
}
]
}`

#### Search Flight
This API endpoint allows you to search flights

URL: [http://localhost:3000/api/flights?flightNumber=Boeing737](http://localhost:3000/api/flights?flightNumber=Boeing737)
Method: GET

Response: Returns a success message along with the details of searched flight.
`{
"code": 200,
"status": "OK",
"message": "Successfully retrieved flights",
"data": [
{
"flightId": 1,
"flightNumber": "Boeing737",
"scheduledTime": "2023-08-05T15:30:00",
"flightType": "OUTBOUND",
"airportCode": "YVR3",
"airportName": "Vancourer International Airport",
"location": "Vancourer, BC",
"status": "DEPARTURE",
"delayed": true
}
]
}`

#### Flight Search Criteria

To perform a flight search, you can utilize the `FlightSearchCriteria` class which provides a structured way to filter flights based on various attributes. Here is an overview of the available search criteria:

- `flightNumber`: The flight number associated with the flight.
- `flightType`: The type of the flight (e.g., inbound, outbound).
- `airportCode`: The code of the airport related to the flight.
- `airportName`: The name of the airport related to the flight.
- `location`: The location or destination of the flight.
- `status`: The status of the flight (e.g., arrival, departure, delay).
- `scheduledTimeFrom`: The earliest scheduled departure or arrival time.
- `scheduledTimeTo`: The latest scheduled departure or arrival time.

### H2 Database
The Flight Service Microservice uses an H2 in-memory database by default. You can modify the database connection properties in the application.properties file if you want to switch to a different database.

### Swagger2 Documentation
The API endpoints are documented using Swagger2. To access the documentation, launch the application and navigate to the following URL in your web browser:

[http://localhost:3000/api/swagger-ui/index.html#/](http://localhost:3000/api/swagger-ui/index.html#/)

#### Testing
To run the unit tests for the Flight Notification Service, use the following command:

`./mvnw test`
