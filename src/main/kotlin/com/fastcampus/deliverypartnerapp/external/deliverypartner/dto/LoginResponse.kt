package com.fastcampus.deliverypartnerapp.external.deliverypartner.dto

data class LoginResponse(
    val deliveryPartnerId: Long,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
)