package com.guilleac.marvelapi.client.exception

sealed class ApiException(val statusCode: Int?,msg: String): RuntimeException(msg) {
    class UnexpectedResponse(statusCode: Int? = 503, msg:String = "Unexpected server response. This request is ok but response is unexpected"): ApiException(statusCode, "response code $statusCode $msg")
    class NoInternetConnectionException(statusCode: Int? = 440, msg:String = "User does not have internet connection") : ApiException(statusCode, "response code $statusCode $msg")
    class ConnectionErrorException(statusCode: Int? = 503, msg:String = "Connection error") : ApiException(statusCode, "response code $statusCode $msg")
    class TimeoutErrorException(statusCode: Int? = 408, msg:String = "Timeout connection error") : ApiException(statusCode, "response code $statusCode $msg")
}
