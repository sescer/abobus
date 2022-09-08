package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Booking(
    @Id
    val booking_no: String = ""
)
