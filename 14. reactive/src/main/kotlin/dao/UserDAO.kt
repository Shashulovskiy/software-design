package dao

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoDatabase
import model.User
import org.bson.Document
import rx.Observable

class UserDAO(private val mongoDatabase: MongoDatabase) {
    fun createUser(user: User): Observable<Boolean> =
        mongoDatabase.getCollection(USER_COLLECTION).insertOne(user.toDocument())
            .asObservable().isEmpty.map { !it }

    fun getUser(id: String): Observable<User> =
        mongoDatabase.getCollection(USER_COLLECTION).find(Filters.eq("id", id))
            .toObservable().map { mapDocumentToUser(it) }

    private fun mapDocumentToUser(document: Document): User =
        User(
            document.getString("id"),
            document.getString("name"),
            document.getString("login"),
            document.getString("currency"),
        )

    companion object {
        const val USER_COLLECTION = "user"
    }
}