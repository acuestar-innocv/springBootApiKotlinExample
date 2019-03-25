package com.api.rest.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "User bad format")
class UserMissingInformationException : RuntimeException()