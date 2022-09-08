package ru.g19213.abobus.service

import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.ScheduleFlight
import ru.g19213.abobus.repository.ScheduleFlightRepository

@Service
class ScheduleFlightService (
    private val repository: ScheduleFlightRepository
){
    fun getFlights(arriving: Boolean, airportName: String, day: Int): List<ScheduleFlight> {
        return if (arriving)
            repository.getArrivingFlights(airportName, day)
        else
            repository.getDepartingFlights(airportName, day)
    }

}
