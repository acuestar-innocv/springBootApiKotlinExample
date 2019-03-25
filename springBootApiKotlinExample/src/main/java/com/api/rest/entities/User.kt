package com.api.rest.entities

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.validation.constraints.Past


@Document(collection = User.COLLECTION_NAME)
class User {

    companion object {
        const val COLLECTION_NAME = "user"
    }

    @Id
    var id: String? = null
    var name: String? = null
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "CET")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past
    var birthdate: Date? = null

    override fun toString(): String {
        return "User(id=$id, name=$name, birthdate=$birthdate)"
    }


}