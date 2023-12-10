package com.fastcampus.deliverypartnerapp.domain.deliveryrequest

enum class DeliveryStatus(description: String) {
    READY("배달 대기"),
    DELIVERY_START("배달 시작"),
    ARRIVAL_AT_STORE("매장 도착"),
    PICKUP_COMPLETE("픽업 완료"),
    DELIVERY_COMPLETE("배달 완료"),
}