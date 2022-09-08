package ru.g19213.abobus.service

import org.springframework.stereotype.Service
import ru.g19213.abobus.entity.BoardingPass
import ru.g19213.abobus.entity.TicketsResponse
import ru.g19213.abobus.repository.BoardingPassRepository
import ru.g19213.abobus.repository.TicketsRepository

@Service
class BoardingPassService (
    private val repository: BoardingPassRepository,
    private val ticketsRepository: TicketsRepository
    ){
    fun getBoardingPass(bookNo: String): List<BoardingPass> {
        var listOfTickets = listOf<TicketsResponse>()
        listOfTickets = ticketsRepository.getListOfTicketNo(bookNo)

        val boardingPasses = mutableListOf<BoardingPass>()
        for (i in listOfTickets.indices){
            repository.insertBoardingPass(listOfTickets[i].ticket_no, listOfTickets[i].flight_id)
            boardingPasses += repository.getBoardingPass(listOfTickets[i].ticket_no, listOfTickets[i].flight_id)
        }
        return boardingPasses
    }

}