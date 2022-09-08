package ru.g19213.abobus.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.g19213.abobus.entity.BoardingPass
import ru.g19213.abobus.entity.TicketsResponse

@Repository
interface BoardingPassRepository:JpaRepository<BoardingPass, Long>  {
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value="INSERT INTO boarding_passes (ticket_no, flight_id, boarding_no, seat_no) " +
            "VALUES (:ticket_no, " +
            ":flight_id," +
            "(SELECT CASE " +
            "WHEN (SELECT COUNT(boarding_no) FROM boarding_passes WHERE flight_id = :flight_id) = 0 THEN 1 " +
            "ELSE (SELECT boarding_no + 1 FROM boarding_passes  WHERE flight_id = :flight_id  ORDER BY boarding_no DESC LIMIT 1) END)," +
            "(SELECT seat_no FROM seats  WHERE aircraft_code = (SELECT aircraft_code FROM flights WHERE flight_id = :flight_id LIMIT 1) and fare_conditions =" +
            "(SELECT fare_conditions FROM ticket_flights tf WHERE tf.ticket_no = :ticket_no LIMIT 1) " +
            "AND NOT EXISTS (  SELECT 1 FROM boarding_passes b WHERE seats.seat_no = b.seat_no AND flight_id = :flight_id) LIMIT 1))")
    fun insertBoardingPass(ticket_no: String, flight_id:Int)


    @Query(nativeQuery=true, value="SELECT bp.flight_id, bp.boarding_no, bp.ticket_no, bp.seat_no, t.passenger_name " +
            "FROM boarding_passes bp JOIN tickets t on bp.ticket_no = t.ticket_no  " +
            "WHERE bp.flight_id = :flight_id AND bp.ticket_no = :ticket_no ")
    fun getBoardingPass(ticket_no: String, flight_id:Int): BoardingPass

}

@Repository
interface TicketsRepository:JpaRepository<TicketsResponse, Long>  {
    @Query(nativeQuery = true, value="SELECT CONCAT(t.ticket_no, tf.flight_id) as id , t.ticket_no as ticket_no, tf.flight_id as flight_id FROM tickets t JOIN ticket_flights tf on t.ticket_no = tf.ticket_no WHERE t.book_ref = :bookNo")
    fun getListOfTicketNo(bookNo:String) :List<TicketsResponse>
}