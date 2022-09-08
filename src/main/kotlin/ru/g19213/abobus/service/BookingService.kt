package ru.g19213.abobus.service

import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.Booking
import ru.g19213.abobus.entity.Path
import ru.g19213.abobus.repository.BookingRepository
import java.util.*


@Service
class BookingService(
    private val repository: BookingRepository
){
    fun getBooking(path: Path, fareConditions: String, passengerName: String, contactData: String): Booking {

        for(i in path.flight_path.indices) {
            val available = repository.checkFlight(path.flight_numbers[i], path.departures[i], path.arrivals[i], path.scheduled_departure[i], fareConditions )
            if (available <= 0){
                return Booking("")
            }
        }
        val bookRef = "_" + UUID.randomUUID().toString().substring(0, 5)
        repository.bookRoute( path.price, bookRef)
        val ticketNo = "_" + UUID.randomUUID().toString().substring(0, 12)
        val passengerId = UUID.randomUUID().toString().substring(0, 20)
        repository.insertTickets( ticketNo, bookRef, passengerId, passengerName, contactData)
        for(i in path.flight_path.indices) {
            val price = repository.getPrice(path.flight_numbers[i], path.departures[i], path.arrivals[i], fareConditions, path.scheduled_departure[i])
            repository.insertTicketFlights(ticketNo, path.flight_numbers[i], fareConditions, price)
        }
        return Booking(bookRef)
    }


    fun getSeats(path: Path, fareConditions: String): List<Int>{
        val listOfSeats = mutableListOf<Int>()
        for(i in path.flight_path.indices) {
            val available = repository.checkFlight(path.flight_numbers[i], path.departures[i], path.arrivals[i], path.scheduled_departure[i], fareConditions )
            listOfSeats += available
        }
        return listOfSeats
    }
}