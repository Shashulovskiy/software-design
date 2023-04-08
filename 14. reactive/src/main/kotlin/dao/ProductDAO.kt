package dao

import com.mongodb.rx.client.MongoDatabase
import model.Product
import org.bson.Document
import rx.Observable

class ProductDAO(private val mongoDatabase: MongoDatabase, private val userDAO: UserDAO) {
    fun createProduct(product: Product): Observable<Boolean> =
        mongoDatabase.getCollection(PRODUCT_COLLECTION).insertOne(product.toDocument())
            .asObservable().isEmpty.map { !it }

    fun listAll(userId: String): Observable<String> {
        val user = userDAO.getUser(userId)
        return user.isEmpty.flatMap { userDoesNotExist ->
            if (userDoesNotExist) {
                return@flatMap Observable.just("User not found")
            } else {
                return@flatMap user.flatMap { user ->
                    mongoDatabase.getCollection(PRODUCT_COLLECTION).find().toObservable()
                        .map { mapDocumentToProductInCurrency(it, user.currency).toString() }
                }
            }

        }
    }

    private fun mapDocumentToProductInCurrency(document: Document, currency: String): Product =
        Product(
            document.getString("name"),
            mapCurrency(
                document.getString("currency"),
                currency,
                document.getInteger("price").toDouble()
            ),
            currency,
        )

    private fun mapCurrency(from: String, to: String, price: Double): Double =
        price * CURRENCY_TO_RUB[from]!! * RUB_TO_CURRENCY[to]!!

    companion object {
        const val PRODUCT_COLLECTION = "product"

        val CURRENCY_TO_RUB = mapOf(
            "rub" to 1.0,
            "usd" to 80.0,
            "eur" to 90.0,
        )

        val RUB_TO_CURRENCY = mapOf(
            "rub" to 1.0 / 1.0,
            "usd" to 1.0 / 80.0,
            "eur" to 1.0 / 90.0,
        )
    }
}