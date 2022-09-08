package ru.g19213.abobus.service

import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.Path
import ru.g19213.abobus.repository.PathRepository
import java.time.LocalDate

@Service
class PathService (
    private val repository: PathRepository
    ){
    fun getPaths(departurePoint: String, arrivalPoint: String, departureDate: LocalDate, connections: Int, fareConditions: String): List<Path> {
        return repository.getPaths(departurePoint, arrivalPoint, departureDate, connections, fareConditions)
    }

}
