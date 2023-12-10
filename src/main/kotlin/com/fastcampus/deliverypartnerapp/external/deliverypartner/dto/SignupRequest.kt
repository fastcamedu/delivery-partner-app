package com.fastcampus.deliverypartnerapp.external.deliverypartner.dto

import com.fastcampus.deliverypartnerapp.domain.bank.BankCode
import com.fastcampus.deliverypartnerapp.domain.deliverypartner.Transportation

data class SignupRequest(
    val email: String,
    val password: String,
    val bankCode: BankCode,
    val bankAccountName: String,
    val bankAccount: String,
    val socialNumber: String,
    val deliveryZone: String,
    val transportation: Transportation,
)