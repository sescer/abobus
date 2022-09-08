package ru.g19213.abobus.controller

import org.springframework.web.bind.annotation.*
import ru.g19213.abobus.entity.BoardingPass
import ru.g19213.abobus.service.BoardingPassService

@RestController
@RequestMapping("/")
class BoardingPassController (
    private val service: BoardingPassService
        ){
    @GetMapping("check-in")
    fun getBoardingPass(@RequestParam bookNo: String): List<BoardingPass>{
        return service.getBoardingPass(bookNo)
    }
}