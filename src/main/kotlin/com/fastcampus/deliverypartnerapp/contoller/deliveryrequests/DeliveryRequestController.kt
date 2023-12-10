package com.fastcampus.deliverypartnerapp.contoller.deliveryrequests

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class DeliveryRequestController{

    @Value("\${google.map.key}")
    private lateinit var googleMapKey: String

    @GetMapping("/delivery-requests/{deliveryPartnerId}")
    fun list(
        @PathVariable("deliveryPartnerId") deliveryPartnerId: Long,
        model: Model
    ): String {
        model.addAttribute(
            "googleMapJsSrcUrl",
            "https://maps.googleapis.com/maps/api/js?key=$googleMapKey&callback=console.debug&libraries=maps,marker&v=beta"
        )
        return "/delivery-request/delivery-request"
    }
}