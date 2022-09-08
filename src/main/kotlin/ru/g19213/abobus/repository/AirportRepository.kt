package ru.g19213.abobus.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.g19213.abobus.entity.Airport

@Repository
interface AirportRepository: JpaRepository<Airport, Long> {
    @Query(value="SELECT ml.airport_code, ml.airport_name ->> :locale AS airport_name FROM airports_data ml WHERE ml.city ->> :locale = :cityName", nativeQuery = true )
    fun getAllAirports(cityName: String, locale: String) : List<Airport>
}