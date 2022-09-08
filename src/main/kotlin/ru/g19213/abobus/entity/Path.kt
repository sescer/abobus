package ru.g19213.abobus.entity

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.array.TimestampArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.Id

@TypeDefs(
    TypeDef(name = "string-array", typeClass = StringArrayType::class) ,
    TypeDef(name = "int-array", typeClass = IntArrayType::class),
    TypeDef(name = "time-array", typeClass = TimestampArrayType::class)


)
@Entity
data class Path(
    val departure_airport: String = "",
    val arrival_airport: String = "",
    @Type(type = "string-array")
    var flight_path: Array<String> = arrayOf(),
    @Type(type = "time-array")
    val scheduled_departure: Array<Timestamp> = arrayOf(),
    val scheduled_arrival: String = "",
    @Id
    @Type(type = "int-array")
    val flight_numbers: Array<Int> = arrayOf(),
    @Type(type = "string-array")
    val departures: Array<String> = arrayOf(),
    @Type(type = "string-array")
    val arrivals: Array<String> = arrayOf(),
    val arrival_city: String = "",
    val price: Int = 0,
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Path

        if (departure_airport != other.departure_airport) return false
        if (arrival_airport != other.arrival_airport) return false
        if (!flight_path.contentEquals(other.flight_path)) return false
        if (scheduled_departure != other.scheduled_departure) return false
        if (scheduled_arrival != other.scheduled_arrival) return false
        if (!flight_numbers.contentEquals(other.flight_numbers)) return false
        if (!departures.contentEquals(other.departures)) return false
        if (!arrivals.contentEquals(other.arrivals)) return false
        if (arrival_city != other.arrival_city) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = departure_airport.hashCode()
        result = 31 * result + arrival_airport.hashCode()
        result = 31 * result + flight_path.contentHashCode()
        result = 31 * result + scheduled_departure.hashCode()
        result = 31 * result + scheduled_arrival.hashCode()
        result = 31 * result + flight_numbers.contentHashCode()
        result = 31 * result + departures.contentHashCode()
        result = 31 * result + arrivals.contentHashCode()
        result = 31 * result + arrival_city.hashCode()
        result = 31 * result + price
        return result
    }
}