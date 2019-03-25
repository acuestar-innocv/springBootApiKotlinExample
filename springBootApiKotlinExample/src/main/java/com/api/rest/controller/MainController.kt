package com.api.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MainController {

    @GetMapping("/")
    fun rootResponse(): ResponseEntity<*> {
        val welcomeMessage = "Welcome to the INNOCV Solutions API"
        return ResponseEntity(welcomeMessage, HttpStatus.OK)
    }

}