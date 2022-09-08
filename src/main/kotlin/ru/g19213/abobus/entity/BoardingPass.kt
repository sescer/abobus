package ru.g19213.abobus.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BoardingPass(
    val ticket_no: String = "",
    val flight_id: Int = 0,
    val seat_no:String = "",
    @Id
    val boarding_no: Int = 0,
    val passenger_name: String = ""


)
