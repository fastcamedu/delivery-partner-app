package com.fastcampus.deliverypartnerapp.contoller.login

import com.fastcampus.deliverypartnerapp.contoller.login.dto.LoginForm
import com.fastcampus.deliverypartnerapp.external.deliverypartner.DeliveryPartnerAdapter
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class LoginController(
    private val deliveryPartnerAdapter: DeliveryPartnerAdapter,
) {

    @GetMapping("/login")
    fun loginForm(model: Model): String {
        val loginForm = LoginForm(
            email = "",
            password = "",
        )
        model.addAttribute("loginForm", loginForm)
        return "/login/login"
    }

    @PostMapping("/login")
    fun login(
        loginForm: LoginForm,
        model: Model,
        httpServletResponse: HttpServletResponse,
    ): String {

        val loginResponse = this.deliveryPartnerAdapter.login(loginForm.email, loginForm.password)
            ?: error("로그인 시 오류가 발생하였습니다")

        httpServletResponse.addCookie(createCookie("deliveryPartnerId", loginResponse.deliveryPartnerId.toString()))
        httpServletResponse.addCookie(createCookie("access-token", loginResponse.accessToken))

        val redirectUrlPath = "/delivery-requests/" + loginResponse.deliveryPartnerId
        return "redirect:$redirectUrlPath"
    }

    private fun createCookie(cookieName: String, cookieValue: String): Cookie {
        val cookie = Cookie(cookieName, cookieValue)
        cookie.path = "/"
        cookie.maxAge = 60 * 60 * 24 * 7
        return cookie
    }
}