package ru.g19213.abobus.service

import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.Airport
import ru.g19213.abobus.repository.AirportRepository

@Service
class AirportService(
    private val repository: AirportRepository
){
    fun getAirports(cityName: String, locale: String?): List<Airport> {
        if (locale == null) {
            return repository.getAllAirports(cityName, "en")
        }
        return repository.getAllAirports(cityName, locale)
    }
}