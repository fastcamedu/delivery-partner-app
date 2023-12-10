package com.fastcampus.deliverypartnerapp.contoller.signup

import com.fastcampus.deliverypartnerapp.contoller.signup.dto.SignupForm
import com.fastcampus.deliverypartnerapp.external.deliverypartner.DeliveryPartnerAdapter
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class SignupController(
    private val deliveryPartnerAdapter: DeliveryPartnerAdapter,
) {
    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    @GetMapping("/signup/signup-step1")
    fun signupStep1(model: Model): String {
        logger.info { ">>> 가입 1단계, 계정정보 입력" }
        return "/signup/signup-step1"
    }

    @PostMapping("/signup/signup-step2")
    fun signupStep2(@ModelAttribute signupForm: SignupForm, model: Model): String {
        logger.info { ">>> 가입 2단계, 급여계좌 입력, $signupForm " }
        model.addAttribute("signupForm", signupForm)
        return "/signup/signup-step2"
    }

    @PostMapping("/signup/signup-step3")
    fun signupStep3(@ModelAttribute signupForm: SignupForm, model: Model): String {
        logger.info { ">>> 가입 3단계, 배달정보 입력, $signupForm" }
        model.addAttribute("signupForm", signupForm)
        return "/signup/signup-step3"
    }

    @PostMapping("/signup/signup-step4")
    fun signupStep4(@ModelAttribute signupForm: SignupForm, model: Model): String {
        logger.info { ">>> 가입 4단계, 계약정보 입력, $signupForm" }
        model.addAttribute("signupForm", signupForm)
        return "/signup/signup-step4"
    }

    @PostMapping("/signup/signup")
    fun signup(@ModelAttribute signupForm: SignupForm, model: Model): String {
        logger.info { ">>> 가입 요청 : $signupForm"}
        val signupRequest = signupForm.toRequest()
        val signupResponse = deliveryPartnerAdapter.signup(signupRequest)
        logger.info { ">>> 가입 처리 응답 : $signupResponse"}
        return "redirect:/signup/signup-success"
    }

    @GetMapping("/signup/signup-success")
    fun signupSuccess(@ModelAttribute signupForm: SignupForm, model: Model): String {
        logger.info { ">>> 가입 성공!!!" }
        return "/signup/signup-success"
    }
}