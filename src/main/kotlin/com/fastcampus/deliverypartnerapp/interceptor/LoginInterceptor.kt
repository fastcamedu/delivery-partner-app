package com.fastcampus.deliverypartnerapp.interceptor

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

class LoginInterceptor: HandlerInterceptor {

    companion object {
        private const val UNKNOWN_USER = "undefined"

        private const val COOKIE_ACCESS_TOKEN = "access-token"
        private const val COOKIE_DELIVERY_PARTNER_ID = "deliveryPartnerId"

        private val logger = KotlinLogging.logger {  }
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info { ">>> LoginInterceptor.preHandle" }
        if (isLoginUser(request)) {
            logger.info { ">>> 로그인 배달 파트너" }
            return super.preHandle(request, response, handler)
        }

        logger.info { ">>> 비로그인 배달 파트너" }
        response.sendRedirect("/")
        return false
    }

    private fun isLoginUser(request: HttpServletRequest): Boolean {
        val accessToken = request.cookies?.firstOrNull { it.name == COOKIE_ACCESS_TOKEN }?.value
        val deliveryPartnerId = request.cookies?.firstOrNull { it.name == COOKIE_DELIVERY_PARTNER_ID }?.value
        if (accessToken == null || deliveryPartnerId == null || UNKNOWN_USER == accessToken || UNKNOWN_USER == deliveryPartnerId) {
            return false
        }
        return true
    }
}