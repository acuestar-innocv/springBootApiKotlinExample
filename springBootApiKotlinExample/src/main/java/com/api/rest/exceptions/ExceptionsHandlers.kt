package com.api.rest.exceptions


import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ExceptionsHandlers {

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleUserNotFoundException(e: UserNotFoundException) = ErrorResponse("USER_NOT_FOUND", "The User was not found")

    @ExceptionHandler(UserMissingInformationException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleContactMissingInformationException(e: UserMissingInformationException) = ErrorResponse("MISSING_INFORMATION", "User bad format")


    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException) = ErrorResponse("METHOD_NOT_SUPPORTED", "Method not supported")


    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleContactMissingInformationException(e: HttpMessageNotReadableException) = ErrorResponse("INVALID_FORMAT", "JSON malformed")


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun MethodArgumentNotValidException(e: MethodArgumentNotValidException) : Map<String,String> {

        val errors = mutableMapOf<String,String>()

        e.bindingResult.allErrors.forEach {
            val fieldName = (it as FieldError).field
            val errorMessage = it.defaultMessage
            errors[fieldName] = errorMessage ?: "ARGUMENT NOT VALID"
        }

        return errors
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleThrowable(e: Throwable) = ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected internal server error occurred")


}