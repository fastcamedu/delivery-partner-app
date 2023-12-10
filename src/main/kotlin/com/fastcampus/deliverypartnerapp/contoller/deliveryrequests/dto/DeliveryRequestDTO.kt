package com.fastcampus.deliverypartnerapp.contoller.deliveryrequests.dto

import com.fastcampus.deliverypartnerapp.domain.deliveryrequest.DeliveryStatus
import java.math.BigDecimal

data class DeliveryRequestDTO(
    val orderDeliveryRequestId: Long,
    val orderId: Long,
    val storeId: Long,
    val customerId: Long,
    val storeAddress: String,
    val deliveryAddress: String,
    val deliveryStatus: DeliveryStatus,
    val deliveryPartnerId: Long?,
    val deliveryFee: BigDecimal,
)