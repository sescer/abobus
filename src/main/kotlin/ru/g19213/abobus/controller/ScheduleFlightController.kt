package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.*
import ru.g19213.abobus.entity.ScheduleFlight
import ru.g19213.abobus.service.ScheduleFlightService


@RestController
@RequestMapping("/")
class ScheduleFlightController(
    private val service: ScheduleFlightService
){
    @GetMapping("scheduleFlights")
    fun getCities(
        @RequestParam arriving: Boolean,
        @RequestParam airportName: String,
        @RequestParam day: Int
    ): List<ScheduleFlight>{
        return service.getFlights(arriving, airportName, day)
    }

}