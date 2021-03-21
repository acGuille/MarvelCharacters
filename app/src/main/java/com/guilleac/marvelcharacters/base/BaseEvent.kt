package com.guilleac.marvelcharacters.base

sealed class BaseEvent {
    object GenericConnectionError : BaseEvent()
    data class ConnectionError(val msg: Int, val code: Int?) : BaseEvent()
}