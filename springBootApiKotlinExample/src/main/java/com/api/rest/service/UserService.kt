package com.api.rest.service

import com.api.rest.entities.User
import com.api.rest.exceptions.UserMissingInformationException
import com.api.rest.exceptions.UserNotFoundException
import com.api.rest.repository.UserRepository
import com.api.rest.service.base.RestService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service
class UserService(val userRepository: UserRepository) : RestService<User> {


    override fun getAll(): ResponseEntity<List<User>> = ResponseEntity(userRepository.findAll(), HttpStatus.OK)


    override fun get(id: String): ResponseEntity<User> {

        val user = userRepository.findById(id).orElseThrow(::UserNotFoundException)

        return ResponseEntity(user, HttpStatus.OK)

    }

    override fun add(entity: User, request: HttpServletRequest): ResponseEntity<User> {
        checkUser(entity)
        val newUser = userRepository.insert(entity)
        val responseHeaders = HttpHeaders()
        responseHeaders.set("Location", generateUserUrl(newUser, request))
        return ResponseEntity(newUser, responseHeaders, HttpStatus.CREATED)
    }


    override fun patchUpdate(id: String, entityUpdates: User): ResponseEntity<User> {

        val existingUser = findUserIfExists(id)
        mergeUser(existingUser, entityUpdates)
        existingUser.id = id
        checkUser(existingUser)
        val updatedContact = userRepository.save(existingUser)
        return ResponseEntity(updatedContact, HttpStatus.OK)
    }

     fun mergeUser(existingUser: User, entityUpdates: User) {

        entityUpdates.birthdate?.let {
            existingUser.birthdate = entityUpdates.birthdate

        }

        entityUpdates.name?.let {
            if (it.isNotBlank())
                existingUser.name = entityUpdates.name
        }

    }

    override fun putUpdate(id: String, entityUpdates: User): ResponseEntity<User> {
        val existingUser = findUserIfExists(id)
        checkUser(entityUpdates)
        entityUpdates.id = id
        userRepository.save(entityUpdates)
        return ResponseEntity(entityUpdates, HttpStatus.OK)
    }


    override fun delete(id: String): ResponseEntity<User> {
        val existingUser = findUserIfExists(id)
        userRepository.deleteById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)

    }


     fun checkUser(entity: User) {

        if (!isUserNameValid(entity.name)) throw UserMissingInformationException()
        if (!isUserBirthdayValid(entity.birthdate)) throw UserMissingInformationException()

    }

    private fun generateUserUrl(newUser: User, request: HttpServletRequest): String {

        val resourcePath = StringBuilder()
        resourcePath.append(request.requestURL)
                .append("/")
                .append(newUser.id)
        return resourcePath.toString()

    }


    fun findUserIfExists(id: String) = userRepository.findById(id).orElseThrow(::UserNotFoundException)!!


    private fun isUserNameValid(name: String?): Boolean = !name.isNullOrBlank()


    private fun isUserBirthdayValid(birthdate: Date?) = birthdate != null


}