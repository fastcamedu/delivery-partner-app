package com.fastcampus.deliverypartnerapp.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException

@ControllerAdvice
class DeliveryPartnerControllerAdvice {

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)

        private const val LOGIN_TEMPLATE = "/signup/signup"
        private const val ERROR_TEMPLATE = "/error"
    }

    @ExceptionHandler(NullPointerException::class, RuntimeException::class)
    fun handle(e: Exception): String {
        logger.error { ">>> handle, ${e.message}" }
        return ERROR_TEMPLATE
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun clientErrorHandle(e: HttpClientErrorException): String {
        logger.error { ">>> clientErrorHandle, ${e.message}" }
        return if( e.statusCode.value() == HttpStatus.UNAUTHORIZED.value()) {
            LOGIN_TEMPLATE
        } else {
            ERROR_TEMPLATE
        }
    }

    @ExceptionHandler(Exception::class)
    fun unknownExceptionHandle(e: Exception): String {
        logger.error { ">>> unknownExceptionHandle, ${e.message}" }
        return ERROR_TEMPLATE
    }
}