package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.*
import ru.g19213.abobus.entity.Booking
import ru.g19213.abobus.entity.BookingRequest
import ru.g19213.abobus.entity.Path
import ru.g19213.abobus.service.BookingService

@RestController
@RequestMapping("/")
class BookingController(
    private val service: BookingService
) {
    @PutMapping("booking")
    fun getBooking( @RequestBody bR: BookingRequest): Booking{
        return service.getBooking(bR.path, bR.fareConditions, bR.passengerName, bR.contactData)
    }
    @PutMapping("seats")
    fun getSeats(@RequestBody bR: BookingRequest): List<Int>{
        return service.getSeats(bR.path, bR.fareConditions)
    }
}