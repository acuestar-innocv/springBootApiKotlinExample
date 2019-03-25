package com.api.rest.controller

import com.api.rest.entities.User
import com.api.rest.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping(path = ["api/users"])
class UserController(val userService: UserService) {


    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> = userService.getAll()


    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<User> = userService.get(id)


    @PostMapping
    fun addUser(@Valid @RequestBody user: User, request: HttpServletRequest): ResponseEntity<User> = userService.add(user, request)

    @PutMapping("/{id}")
    fun putUpdateUser(@PathVariable id: String, @RequestBody @Valid user:User) = userService.putUpdate(id,user)

    @PatchMapping("/{id}")
    fun patchUpdateUser (@PathVariable id: String,@RequestBody @Valid user:User) = userService.patchUpdate(id,user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) = userService.delete(id)


}