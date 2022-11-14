package config

@kotlinx.serialization.Serializable
data class ApiConfig(val protocol: String, val apiHost: String, val accessToken: String, val apiVersion: String)