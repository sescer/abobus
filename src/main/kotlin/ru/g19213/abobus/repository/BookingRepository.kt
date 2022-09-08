package ru.g19213.abobus.repository

import com.google.gson.JsonObject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.g19213.abobus.entity.Booking
import java.sql.Timestamp

@Repository
interface BookingRepository:JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value=" INSERT INTO bookings (book_ref, book_date, total_amount) VALUES (:bookRef, now(), :price )")
    fun bookRoute(price:Int, bookRef:String)

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value="INSERT INTO tickets (ticket_no, book_ref, passenger_id, passenger_name, contact_data ) VALUES (:ticketNo, :bookRef, :passengerId,:passengerName, CAST(:contactData as jsonb)); ")
    fun insertTickets(ticketNo:String, bookRef: String, passengerId:String, passengerName: String, contactData: String)

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value="INSERT INTO ticket_flights (ticket_no, flight_id, fare_conditions, amount) VALUES (:ticketNo, :flightId, :fareConditions, :price)")
    fun insertTicketFlights(ticketNo:String, flightId: Int, fareConditions: String, price:Int)

    @Query(nativeQuery = true, value="(SELECT DISTINCT MIN(flp.amount) FROM flight_price flp WHERE flp.flight_id = :flightId AND flp.departure_airport = :departureAirport AND flp.arrival_airport = :arrivalAirport AND flp.fare_conditions = :fareConditions AND flp.scheduled_departure = :departureDate)")
    fun getPrice(flightId: Int, departureAirport: String, arrivalAirport: String, fareConditions: String, departureDate: Timestamp): Int

    @Query(nativeQuery = true, value="WITH tickets_count( " +
            "tickets ) AS( " +
            "SELECT COUNT(*) " +
            "FROM ticket_flights tf " +
            "WHERE tf.flight_id = :flightId AND fare_conditions = :fareConditions ) " +
            "SELECT COUNT(s.seat_no) - tb.tickets " +
            "FROM seats s, tickets_count tb " +
            "WHERE s.aircraft_code = (SELECT fl.aircraft_code FROM flights fl WHERE fl.flight_id = :flightId AND CAST(fl.scheduled_departure AS date) = to_date(:departureDate, 'YYYY-MM-DD') AND fl.departure_airport = :departureAirport AND fl.arrival_airport = :arrivalAirport) " +
            "AND s.fare_conditions =  :fareConditions " +
            "group by tb.tickets")
    fun checkFlight(flightId: Int, departureAirport:String, arrivalAirport:String, departureDate: Timestamp, fareConditions: String) : Int
}