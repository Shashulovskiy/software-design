package handler

import dao.ProductDAO
import dao.UserDAO
import io.netty.handler.codec.http.HttpMethod
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import model.Product
import model.User
import rx.Observable

class RequestHandler(
    private val catalogDAO: ProductDAO,
    private val userDAO: UserDAO,
) {
    fun handleRequest(req: HttpServerRequest<*>): Observable<String> {
        val params = req.queryParameters
        when {
            req.decodedPath == "/users" && req.httpMethod == HttpMethod.POST -> {
                return handleNewUser(params)
            }
            req.decodedPath == "/user" && req.httpMethod == HttpMethod.GET -> {
                return handleGetUser(params)
            }

            req.decodedPath == "/products" && req.httpMethod == HttpMethod.POST -> {
                return handleNewProduct(params)
            }
            req.decodedPath == "/products" && req.httpMethod == HttpMethod.GET -> {
                return handleListProducts(params)
            }
        }

        throw Exception("Not found")
    }

    private fun handleListProducts(params: MutableMap<String, MutableList<String>>) =
        catalogDAO.listAll(params["userId"]!![0]).map { it.toString() }

    private fun handleNewProduct(params: MutableMap<String, MutableList<String>>) =
        catalogDAO.createProduct(
            Product(
                params["name"]!![0],
                params["price"]!![0].toDouble(),
                params["currency"]!![0],
            )
        ).map { it.toString() }

    private fun handleGetUser(params: Map<String, MutableList<String>>?) =
        userDAO.getUser(
            params!!["id"]!![0],
        ).map { it.toString() }

    private fun handleNewUser(params: MutableMap<String, MutableList<String>>) =
        userDAO.createUser(
            User(
                params["id"]!![0],
                params["name"]!![0],
                params["login"]!![0],
                params["currency"]!![0],
            )
        ).map { it.toString() }

}