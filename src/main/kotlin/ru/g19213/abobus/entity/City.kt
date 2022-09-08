package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class City(
    @Id
    val airport_code: String = "",
    val airport_name: String = "",
    val city: String = "",
    val coordinates: String = "",
    val timezone: String = ""

)
