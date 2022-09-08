package ru.g19213.abobus.entity

data class BookingRequest(
    val path: Path,
    val fareConditions:String,
    val passengerName: String,
    val contactData: String
)
