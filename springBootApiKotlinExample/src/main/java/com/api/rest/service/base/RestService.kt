package com.api.rest.service.base

import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest


internal interface RestService<T> {


    fun getAll(): ResponseEntity<List<T>>

    fun get(id: String): ResponseEntity<T>

    fun add(entity: T, request: HttpServletRequest): ResponseEntity<T>

    fun patchUpdate(id: String, entityUpdates: T): ResponseEntity<T>

    fun putUpdate(id: String, entityUpdates: T): ResponseEntity<T>

    fun delete(id: String): ResponseEntity<T>


}