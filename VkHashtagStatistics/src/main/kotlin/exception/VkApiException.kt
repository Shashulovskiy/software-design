package exception

class VkApiException(errorResponse: String) : Exception(errorResponse)