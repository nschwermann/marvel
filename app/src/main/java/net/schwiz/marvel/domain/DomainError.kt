package net.schwiz.marvel.domain

sealed class DomainError(val cause : Throwable? = null, val message : String? = null) {

    class UnknownError(cause: Throwable? = null, message: String? = null) : DomainError(cause = cause, message = message)
}