package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ScheduleFlight(
    @Id
    val flight_no: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val airport_name : String = "",
)
