**Flight Booking System**

----
**Overview**

This project implements a Flight Booking System, allowing users to manage flights, seats, bookings, and payments. The system is designed to facilitate the process of booking flights by providing a structured approach to handle related entities.

----
**Entities**

**Flight**

The Flight entity represents a flight in the system. It contains the following fields:

_id:_ A unique identifier for each flight (generated automatically).
_flightNumber:_ The flight's unique number for identification.
_origin:_ The departure location of the flight.
_destination:_ The arrival location of the flight.
_departureTime:_ The scheduled departure time of the flight.
_price:_ The cost of the flight.

**Relationships:**
_seats:_ A one-to-many relationship with the Seat entity, representing the seats available for the flight. The seats field is annotated with @JsonIgnore to prevent serialization to JSON, avoiding circular references during API responses.

---

**Seat**

The Seat entity represents a seat on a flight. It consists of the following fields:

_id:_ A unique identifier for each seat (generated automatically).
_seatNumber:_ The designated number for the seat.
_status:_ The current status of the seat, which can be AVAILABLE, BOOKED, or RESERVED.

**Relationships:**

_flight:_ A many-to-one relationship with the Flight entity, indicating which flight the seat belongs to.
_booking:_ A one-to-one relationship with the Booking entity, representing the booking associated with the seat.

----

**Payment**

The Payment entity represents a payment made for a booking. It includes the following fields:

_id:_ A unique identifier for each payment (generated automatically).
_price:_ The amount paid for the booking.
_bankResponse:_ The response received from the bank after processing the payment.

----

**Booking**

The Booking entity represents a reservation for a seat on a flight. It includes the following fields:

_id:_ A unique identifier for each booking (generated automatically).
_seat:_ A one-to-one relationship with the Seat entity, indicating the seat that has been booked.
_flight:_ A many-to-one relationship with the Flight entity, indicating the flight associated with the booking.
_description:_ A description or notes related to the booking.

-----

-----
**Controller Structure**

The application follows a standard layered architecture pattern using Controllers, Services, and Repositories. This separation of concerns enhances maintainability and readability.

**Controllers**

**FlightController:** Manages flight-related operations.

* addFlight(Flight flight): Adds a new flight to the system. Validates flight number before addition.
* getAllFlights(): Retrieves a list of all flights.
* deleteFlight(Long flightId): Deletes a flight by its ID after checking its existence.
* updateFlight(Flight flight): Updates an existing flight's details after validating the flight number.

**SeatController:** Manages seat-related operations for flights.

* addSeat(List<String> seatNumbers, Long flightId): Adds seats to a specific flight. Validates seat numbers before addition.
* getAllSeats(): Retrieves a list of all seats along with their flight details.
* getSeatById(Long seatId): Retrieves a seat by its ID.
* deleteSeat(Long seatId): Deletes a seat by its ID after checking its existence.
* updateSeat(Seat seat): Updates an existing seat's details after validating the seat number.
* Services
* FlightServiceImp: Implements business logic related to flights.

* addFlight(Flight flight): Adds a new flight and generates its seats.
* getAllFlights(): Retrieves all flights from the repository.
* getFlightById(Long flightId): Retrieves a flight by its ID.
* deleteFlight(Long id): Deletes a flight and its associated seats.
* updateFlight(Flight flight): Updates an existing flight's details.
* generateFlightSeatNumbers(): Generates a set of seat numbers for a flight.

**SeatServiceImp:** Implements business logic related to seats.

* addSeat(List<String> seatNumbers, Long flightId): Adds seats to a specific flight and processes payment.
* getAllSeats(): Retrieves all seats along with their flight details.
* getSeatById(Long seatId): Retrieves a seat by its ID.
* deleteSeat(Long seatId): Deletes a seat by its ID.
* updateSeat(Seat seat): Updates an existing seat's details.
* addBooking(Seat seat): Adds a booking for a reserved seat and updates its status.

**Validation**
The application includes validation classes (FlightValidation and SeatValidation) to ensure that flight and seat data are correct before processing requests.

**Dependencies**
Spring Boot
Lombok (for concise code)
JPA/Hibernate (for data persistence)
Other dependencies as required (e.g., logging)

-----
