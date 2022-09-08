package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Airport(
    @Id
    val airport_code: String = "",
    val airport_name: String = ""
)
