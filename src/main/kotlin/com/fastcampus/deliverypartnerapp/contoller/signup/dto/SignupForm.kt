package com.fastcampus.deliverypartnerapp.contoller.signup.dto

import com.fastcampus.deliverypartnerapp.domain.bank.BankCode
import com.fastcampus.deliverypartnerapp.domain.deliverypartner.Transportation
import com.fastcampus.deliverypartnerapp.external.deliverypartner.dto.SignupRequest

data class SignupForm(
    val email: String?,
    val password1: String?,
    val password2: String?,
    val bankCode: BankCode?,
    val bankAccountName: String?,
    val bankAccount: String?,
    val socialNumber: String?,
    val deliveryZone: String?,
    val transportation: Transportation?,
) {
    fun toRequest(): SignupRequest {

        if (email == null) {
            error("가입에 필요한 이메일 정보가 없습니다.")
        }

        if (password1 == null) {
            error("가입에 필요한 비밀번호 정보가 없습니다.")
        }

        if (password2 == null) {
            error("가입에 필요한 비밀번호 정보가 없습니다.")
        }

        if (bankCode == null) {
            error("가입에 필요한 은행 정보가 없습니다.")
        }

        if (bankAccount == null) {
            error("가입에 필요한 은행 계좌 정보가 없습니다.")
        }

        if (bankAccountName == null) {
            error("가입에 필요한 은행계좌명 정보가 없습니다.")
        }

        if (socialNumber == null) {
            error("가입에 필요한 주민번호 정보가 없습니다.")
        }

        if (deliveryZone == null) {
            error("가입에 필요한 시군 정보가 없습니다.")
        }

        if (transportation == null) {
            error("가입에 필요한 이동수단 정보가 없습니다.")
        }

        return SignupRequest(
            email = this.email,
            password = this.password1,
            bankCode = this.bankCode,
            bankAccountName = this.bankAccountName,
            bankAccount = this.bankAccount,
            socialNumber = this.socialNumber,
            deliveryZone = this.deliveryZone,
            transportation = this.transportation,
        )
    }
}