package model

import org.bson.Document

data class User(
    val id: String,
    val name: String,
    val login: String,
    val currency: String,
) {
    fun toDocument() = Document(
        mapOf(
            "id" to id,
            "name" to name,
            "login" to login,
            "currency" to currency,
        )
    )
}