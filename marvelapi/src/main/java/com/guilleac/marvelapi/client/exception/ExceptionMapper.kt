package com.guilleac.marvelapi.client.exception

import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError

class ExceptionMapper {
    companion object {
        fun mapVolleyErrorToApiException(volleyError: VolleyError): ApiException {
            return when (volleyError) {
                is NetworkError -> ApiException.NoInternetConnectionException()
                is NoConnectionError -> ApiException.ConnectionErrorException()
                is TimeoutError -> ApiException.TimeoutErrorException()
                else -> ApiException.ConnectionErrorException()
            }
        }
    }
}