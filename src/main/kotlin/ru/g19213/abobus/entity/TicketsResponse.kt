package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class TicketsResponse(
    @Id
    val id: String = "",
    val ticket_no: String = "",
    val flight_id: Int = 0
)
