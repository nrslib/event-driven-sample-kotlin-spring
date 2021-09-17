package com.example.scrumapp.webapplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScrumApplication

fun main(args: Array<String>) {
    runApplication<ScrumApplication>(*args)
}
