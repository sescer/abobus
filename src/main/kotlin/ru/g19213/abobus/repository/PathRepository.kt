package ru.g19213.abobus.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.g19213.abobus.entity.Path
import java.time.LocalDate

// TODO: Сделать результаты уникальными + доработать рекурсию для того, чтобы можно было считать коннкшены
@Repository
interface PathRepository: JpaRepository<Path, Int>{
    @Query( nativeQuery = true, value=" WITH RECURSIVE r ( " +
            "departure_airport, " +
            "arrival_airport, " +
            "flight_path, " +
            "scheduled_departure, " +
            "scheduled_arrival, " +
            "flight_numbers, " +
            "departures, " +
            "arrivals, " +
            "arrival_city, " +
            "price) AS (SELECT departure_airport, " +
            "arrival_airport ," +
            "CAST(ARRAY[ad.city ->> 'en'] AS bpchar[]), " +
            "CAST(ARRAY[scheduled_departure] AS timestamp with time zone[]), " +
            "scheduled_arrival, " +
            "CAST(ARRAY[flight_id] AS int[])," +
            "CAST(ARRAY[departure_airport] AS bpchar[]) , " +
            "CAST(ARRAY[arrival_airport] AS bpchar[]), " +
            "ad2.city ->> 'en', " +
            "CAST((SELECT DISTINCT MIN(flp.amount) FROM flight_price flp WHERE flp.departure_airport = ad.airport_code AND flp.arrival_airport = ad2.airport_code AND flp.fare_conditions = :fareConditions AND CAST(flp.scheduled_departure AS date) = to_date(:departureDate, 'YYYY-MM-DD')) AS numeric) " +
            "FROM flights " +
            "JOIN airports_data ad ON departure_airport = ad.airport_code " +
            "JOIN airports_data ad2 ON arrival_airport = ad2.airport_code " +
            "WHERE " +
            "(departure_airport = :departurePoint OR ad.city ->> 'en' = :departurePoint) " +
            "AND CAST(scheduled_departure AS date) = to_date(:departureDate, 'YYYY-MM-DD') " +
            "AND (SELECT DISTINCT count(*) FROM flight_price flp WHERE flp.departure_airport = ad.airport_code AND flp.arrival_airport = ad2.airport_code AND flp.fare_conditions = :fareConditions) > 0 " +
            "UNION ALL " +
            "SELECT " +
            "rec.departure_airport, " +
            "f.arrival_airport," +
            "(rec.flight_path || (ad2.city ->> 'en')), " +
            "(rec.scheduled_departure || f.scheduled_departure), " +
            "f.scheduled_arrival, " +
            "(rec.flight_numbers || f.flight_id), " +
            "(rec.departures || f.departure_airport), " +
            "(rec.arrivals || f.arrival_airport), " +
            "ad.city ->> 'en', " +
            "rec.price + (SELECT DISTINCT MIN(flp.amount) FROM flight_price flp WHERE flp.departure_airport = ad2.airport_code AND flp.arrival_airport = ad.airport_code AND flp.fare_conditions = :fareConditions) " +
            "FROM flights f " +
            "JOIN airports_data ad ON f.arrival_airport = ad.airport_code " +
            "JOIN airports_data ad2 ON f.departure_airport = ad2.airport_code " +
            "JOIN r rec ON ad2.city ->> 'en' = rec.arrival_city " +
            "WHERE " +
            "NOT ad.city ->> 'en' = ANY(rec.flight_path) " +
            "AND f.scheduled_departure > rec.scheduled_arrival " +
            "AND f.scheduled_departure - rec.scheduled_arrival < INTERVAL '1 day' " +
            "AND cardinality(rec.flight_path) <= :connections " +
            "AND (SELECT DISTINCT COUNT(*) FROM flight_price flp WHERE flp.departure_airport = ad2.airport_code AND flp.arrival_airport = ad.airport_code AND flp.fare_conditions = :fareConditions) > 0 ) " +
            "SELECT  res.departure_airport, res.arrival_airport, res.flight_path, res.scheduled_departure, res.scheduled_arrival, res.flight_numbers, res.departures, res.arrivals, res.arrival_city, res.price " +
            "FROM r res " +
            "JOIN airports_data ad ON res.arrival_airport = ad.airport_code " +
            "WHERE res.arrival_airport = :arrivalPoint OR ad.city ->> 'en' = :arrivalPoint ")
    fun getPaths(departurePoint: String, arrivalPoint: String, departureDate: LocalDate, connections: Int, fareConditions: String): List<Path>

}