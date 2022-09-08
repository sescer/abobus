package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.g19213.abobus.entity.City
import ru.g19213.abobus.service.CityService

@RestController
@RequestMapping("/")
class CityController (
    private val service: CityService
){
    @GetMapping("cities")
    fun getCities(@RequestParam locale: String?): List<City>{
        return service.getCities(locale)
    }

}