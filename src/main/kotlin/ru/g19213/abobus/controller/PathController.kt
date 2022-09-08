package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.g19213.abobus.entity.Path
import ru.g19213.abobus.service.PathService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/")
class PathController (
    private val service: PathService
){
    @GetMapping("paths")
    fun getPaths(
        @RequestParam departurePoint: String,
        @RequestParam arrivalPoint: String,
        @RequestParam departureDate: String,
        @RequestParam connections: Int,
        @RequestParam fareConditions: String
    ): List<Path>{
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(departureDate, firstApiFormat)
        return service.getPaths(departurePoint, arrivalPoint, date, connections, fareConditions)
    }
}