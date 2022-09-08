package ru.g19213.abobus.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.g19213.abobus.entity.City

@Repository
interface CityRepository: JpaRepository<City, Long>{

    @Query(value = "SELECT ml.airport_code, ml.airport_name ->> :locale as airport_name, ml.city ->> :locale as city, ml.coordinates, ml.timezone FROM airports_data ml", nativeQuery = true)
    fun getAllCities(locale: String): List<City>
}