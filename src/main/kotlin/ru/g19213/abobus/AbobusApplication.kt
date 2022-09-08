package ru.g19213.abobus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AbobusApplication

fun main(args: Array<String>) {
    runApplication<AbobusApplication>(*args)
}
