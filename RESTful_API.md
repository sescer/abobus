
### RESTful API:

* **GET** /cities?locale=`<language_code>`
  * *status code* **200 OK**
  * default language_code - english
  * *returns* a list of cities
  * Example:

  > /cities?locale=ru

  ```json
  [
    {
        "airport_code": "YKS",
        "airport_name": "Якутск",
        "city": "Якутск",
        "coordinates": "(129.77099609375,62.093299865722656)",
        "timezone": "Asia/Yakutsk"
    },
    ...
  ]
  ```

* **GET** /airports?city=`<city>`&locale=`<language_code>`
  * *status code* **200 OK**
  * default language_code - english
  * *returns* a list of airports for this city
  * Example:

  > /airports?city=Novosibirsk

  ```json
  [
    {
        "airport_code": "OVB",
        "airport_name": "Tolmachevo Airport"
    }
  ]
  ```

* **GET** /scheduleFlights?arriving=`<true/false>`&airportName=`<airport>`&day=`<[1-7]>`
  * *status code* **200 OK**
  * *returns* a list of flights for this airport
  * Example:

  >/scheduleFlights?arriving=false&airportName=OVB&day=1

  ```json
   [
    {
        "flight_no": "PG0047",
        "hour": 16,
        "minute": 55,
        "airport_name": "KLF"
    },
    {
        "flight_no": "PG0083",
        "hour": 10,
        "minute": 15,
        "airport_name": "PYJ"
    },
    ...
    ]
  ```

* **GET** /paths?departurePoint=`<city/airport>`&arrivalPoint=`<city/airport>`&departureDate=`<yyyy-MM-dd>`&connections=`<[0-]>`&fareConditions=`<Economy/Comfort/Business>`
  * *status code* **200 OK**
  * *returns* a list of paths
  * Example:

  > /paths?departurePoint=OVB&arrivalPoint=SVO&departureDate=2017-08-12&connections=1&fareConditions=Economy

  ```json
  [
    {
        "departure_airport": "OVB",
        "arrival_airport": "SVO",
        "flight_path": ["Novosibirsk"],
        "scheduled_departure": "2017-08-12 18:20:00+07",
        "scheduled_arrival": "2017-08-12 21:45:00+07",
        "flight_numbers": [19611],
        "departures": ["OVB"],
        "arrivals": ["SVO"],
        "arrival_city": "Moscow",
        "price": 28000
    },
    {
        "departure_airport": "OVB",
        "arrival_airport": "SVO",
        "flight_path": "[Novosibirsk,Krasnoyarsk]",
        "scheduled_departure": "2017-08-12 17:30:00+07",
        "scheduled_arrival": "2017-08-13 17:05:00+07",
        "flight_numbers": [19807,27499],
        "departures": ["OVB","KJA"],
        "arrivals": ["KJA","SVO"],
        "arrival_city": "Moscow",
        "price": 39600
    },
       ...
    ]
  ```

* **PUT** /booking
  * body: { path, passengerId, passengerName, contactData, fareConditions }
  * *status code* **200 OK**
  * *returns* ticket number for the booking
  * Example:

  > /booking

  ```json
    {
    "path": {
        "departure_airport": "CSY",
        "arrival_airport": "SVO",
        "flight_path": ["Cheboksary"],
        "scheduled_departure": "2017-05-17 16:05:00+07",
        "scheduled_arrival": "2017-05-17 16:55:00+07",
        "flight_numbers": [57376],
        "departures": ["CSY"],
        "arrivals": ["SVO"],
        "arrival_city": "Moscow",
        "price": 18500
    },
    "fareConditions": "Business",
    "passengerName": "KIRA SIDOROVA",
    "contactData": "+70125366530"
   }
  ```

   > Return:

  ```json
  [
    {
        "bookingNo": "DA7166"
    }
  ]

  ```

* **GET** /check-in?bookNo=`<ticketNumber>`
  * *status code* **200 OK**
  * *returns* a list of boarding passes
  * Example:

  >/check-in?bookNo=DA7166

  ```json
    [
    {
        "seat_no": "17E",
        "boarding_no": 6,
        "flight_id": 57376,
        "passenger_name": "NINA BELOVA"
    },
    {
        "seat_no": "1D",
        "boarding_no": 9,
        "flight_id": 57376,
        "passenger_name": "KIRA SIDOROVA"
    }
  ]
  ```
