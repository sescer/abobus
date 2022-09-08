package ru.g19213.abobus.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.g19213.abobus.entity.ScheduleFlight

@Repository
interface ScheduleFlightRepository: JpaRepository<ScheduleFlight, Long> {
    @Query(value = "SELECT DISTINCT fl.flight_no, " +
            "extract (HOUR from fl.scheduled_arrival) as hour," +
            "extract (MINUTE from fl.scheduled_arrival) as minute," +
            "fl.departure_airport  as airport_name "+
            "FROM flights fl " +
            "WHERE fl.arrival_airport= (:airportName) and " +
            "extract (ISODOW from fl.scheduled_arrival) =(:day)" +
            "GROUP BY flight_no," +
            "fl.scheduled_arrival, airport_name " +
            "ORDER BY fl.flight_no",
        nativeQuery = true)
    fun getArrivingFlights(airportName: String, day: Int): List<ScheduleFlight>

    @Query(value = "SELECT DISTINCT fl.flight_no, " +
            "extract (HOUR from fl.scheduled_departure) as hour," +
            "extract (MINUTE from fl.scheduled_departure) as minute," +
            "fl.arrival_airport as airport_name " +
            "FROM flights fl " +
            "WHERE fl.departure_airport= (:airportName) and " +
            "extract (ISODOW from fl.scheduled_departure) =(:day)"+
            "GROUP BY flight_no," +
            "fl.scheduled_departure, airport_name " +
            "ORDER BY fl.flight_no",
        nativeQuery = true)
    fun getDepartingFlights( airportName: String, day: Int): List<ScheduleFlight>
}