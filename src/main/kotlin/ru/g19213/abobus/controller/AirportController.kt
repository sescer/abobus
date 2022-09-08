package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.*
import ru.g19213.abobus.entity.Airport
import ru.g19213.abobus.service.AirportService

// TODO: create language support
@RestController
@RequestMapping("/")
class AirportController (
    private val service: AirportService
){
    @GetMapping("airports/{cityName}")
    fun getAirports(
        @PathVariable cityName: String,
        @RequestParam locale: String?
    ): List<Airport>{
        return service.getAirports(cityName, locale)
    }

}