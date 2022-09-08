package ru.g19213.abobus.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.City
import ru.g19213.abobus.repository.CityRepository


@Service
class CityService (
    private val repository: CityRepository
){
    fun getCities(locale: String?): List<City> {
        if (locale == null) {
            return repository.getAllCities("en")
        }
        return repository.getAllCities(locale)
    }

}