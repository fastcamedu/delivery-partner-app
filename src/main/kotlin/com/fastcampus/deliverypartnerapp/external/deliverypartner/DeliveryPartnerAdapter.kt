package com.fastcampus.deliverypartnerapp.external.deliverypartner

import com.fastcampus.deliverypartnerapp.external.deliverypartner.dto.LoginResponse
import com.fastcampus.deliverypartnerapp.external.deliverypartner.dto.SignupRequest
import com.fastcampus.deliverypartnerapp.external.deliverypartner.dto.SignupResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class DeliveryPartnerAdapter(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    @Value("\${apis.delivery-partner-api.host}")
    private lateinit var deliveryPartnerApiUrl: String

    @Value("\${apis.delivery-partner-api.login}")
    private lateinit var loginPath: String

    @Value("\${apis.delivery-partner-api.signup}")
    private lateinit var signUpUrl: String

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    fun login(email: String, password: String): LoginResponse? {

        val loginFullPath = "$deliveryPartnerApiUrl$loginPath"
        logger.info { ">>> 로그인 요청, email: $email, url: $loginFullPath" }

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val loginRequestParam = JSONObject()

        loginRequestParam.put("email", email)
        loginRequestParam.put("password", password)

        val request = HttpEntity(loginRequestParam.toString(), httpHeaders)

        val responseEntity = restTemplate.postForEntity(loginFullPath, request, LoginResponse::class.java)
        return responseEntity.body
    }

    fun signup(signupRequest: SignupRequest): SignupResponse {
        val signupFullPath = "$deliveryPartnerApiUrl$signUpUrl"
        logger.info { ">>> 가입 요청, signupRequest: $signupRequest, url: $signupFullPath" }

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val signupRequestParam = JSONObject()
        signupRequestParam.put("email", signupRequest.email)
        signupRequestParam.put("password", signupRequest.password)
        signupRequestParam.put("bankCode", signupRequest.bankCode)
        signupRequestParam.put("bankAccountName", signupRequest.bankAccountName)
        signupRequestParam.put("bankAccount", signupRequest.bankAccount)
        signupRequestParam.put("socialNumber", signupRequest.socialNumber)
        signupRequestParam.put("deliveryZone", signupRequest.deliveryZone)
        signupRequestParam.put("transportation", signupRequest.transportation)

        val request = HttpEntity(signupRequestParam.toString(), httpHeaders)

        val responseEntity = restTemplate.postForEntity(signupFullPath, request, SignupResponse::class.java)
        return responseEntity.body ?: error("가입 시 오류가 발생하였습니다.")
    }
}