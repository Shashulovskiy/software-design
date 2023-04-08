package model

import org.bson.Document

data class Product(
    val name: String,
    val price: Double,
    val currency: String,
) {
    fun toDocument() = Document(
        mapOf(
            "name" to name,
            "price" to price,
            "currency" to currency,
        )
    )
}