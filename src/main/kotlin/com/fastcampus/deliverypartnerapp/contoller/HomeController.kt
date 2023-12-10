package com.fastcampus.deliverypartnerapp.contoller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    @GetMapping("/", "")
    fun index(
        @CookieValue deliveryPartnerId: Long?,
        model: Model,
    ): String {
        logger.info { ">>> 홈 접속, $deliveryPartnerId" }
        if (deliveryPartnerId == null) {
            return "index"
        }
        return "redirect:/delivery-requests/$deliveryPartnerId"
    }
}